package com.example.data.remote

import com.example.BuildConfig
import com.example.data.model.THIRTEEN_CORE_LEAVES
import com.example.data.model.LeafVerse
import com.example.data.model.PrayerMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

data class PastoralResponse(
    val text: String,
    val verse1925: String,
    val reference: String,
    val comfortNotes: String,
    val pastoralNeed: String,
    val isFromGeminiApi: Boolean = false
)

class GeminiDevotionalService {

    // 60-second timeouts as mandated by Gemini skill
    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    suspend fun getCounsel(
        userMessage: String,
        conversationHistory: List<PrayerMessage> = emptyList(),
        customApiKey: String = ""
    ): PastoralResponse = withContext(Dispatchers.IO) {
        val effectiveKey = when {
            customApiKey.isNotBlank() -> customApiKey.trim()
            isConfiguredKey(BuildConfig.GEMINI_API_KEY) -> BuildConfig.GEMINI_API_KEY.trim()
            else -> ""
        }

        if (effectiveKey.isNotBlank()) {
            try {
                val remoteResp = callGeminiMultiTurnApi(userMessage, conversationHistory, effectiveKey)
                if (remoteResp != null) return@withContext remoteResp
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        // Graceful 100% offline Biblical 1925 reasoning engine fallback
        generateLocalPastoralResponse(userMessage)
    }

    private fun isConfiguredKey(key: String?): Boolean {
        if (key.isNullOrBlank()) return false
        if (key == "MY_GEMINI_API_KEY" || key.startsWith("YOUR_")) return false
        return true
    }

    private fun callGeminiMultiTurnApi(
        newMessage: String,
        history: List<PrayerMessage>,
        apiKey: String
    ): PastoralResponse? {
        val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=$apiKey"

        val systemInstructionText = """
            Bạn là Đấng Lắng Nghe Nhân Từ (Tâm Sự Với Chúa) trong ứng dụng 'Cây Sự Sống'.
            Hãy đồng hành, an ủi và dẫn lối người dùng theo 13 nhu cầu tâm lý & tâm linh:
            1. Được yêu thương (yêu vô điều kiện)
            2. Được chấp nhận (ân điển tiếp nhận không vì hoàn hảo)
            3. Được thuộc về (thuộc về nhà đời đời của Đức Chúa Trời)
            4. Được nhìn nhận (Chúa thấu suốt và trân trọng)
            5. Có giá trị (giá trị có trước mọi thành tích)
            6. Được an toàn (nương náu dưới bóng cánh Đấng Toàn Năng)
            7. Có người bảo vệ (Chúa là đồn lũy vững bền)
            8. Được lắng nghe (Chúa lắng nghe tiếng lòng dốc đổ)
            9. Được thấu hiểu (Chúa cảm thương sự yếu đuối)
            10. Được nghỉ ngơi (yên nghỉ thanh thản trong Chúa)
            11. Được tha thứ (huyết Chúa tha thứ và tẩy sạch)
            12. Được phục hồi (thất bại không phải điểm kết thúc, Chúa đền bù)
            13. Được tự do (tự do thật trong Thánh Linh)

            QUY TẮC BẮT BUỘC:
            - Dùng đúng hệ từ ngữ Kinh Thánh Bản Dịch 1925: 'Đức Chúa Trời', 'Đức Giê-hô-va', 'Cha về phần linh', 'Chúa Jêsus', 'Đức Thánh Linh'.
            - Luôn trích dẫn ít nhất 1 câu gốc chuẩn xác từ KINH THÁNH BẢN DỊCH TRUYỀN THỐNG 1925 (tiếng Việt) kèm địa hạt sách/chương/câu.
            - Không dùng giáo lý Ba Ngôi gộp chung 3 Đấng thành 1.
            - Giọng văn ấm áp, yêu thương, xoa dịu tổn thương, không kết án.
        """.trimIndent()

        val jsonBody = JSONObject().apply {
            // System instruction
            put("systemInstruction", JSONObject().apply {
                put("parts", JSONArray().apply {
                    put(JSONObject().put("text", systemInstructionText))
                })
            })

            // Multi-turn contents array
            val contentsArr = JSONArray()

            // Include last 8 turns of conversation for context
            val recentTurns = history.takeLast(8)
            for (msg in recentTurns) {
                val role = if (msg.sender == "user") "user" else "model"
                val partText = if (msg.sender == "user") {
                    msg.text
                } else {
                    "${msg.text}\n[${msg.reference ?: ""}]: ${msg.verse1925 ?: ""}"
                }
                contentsArr.put(JSONObject().apply {
                    put("role", role)
                    put("parts", JSONArray().apply {
                        put(JSONObject().put("text", partText))
                    })
                })
            }

            // Current message
            contentsArr.put(JSONObject().apply {
                put("role", "user")
                put("parts", JSONArray().apply {
                    put(JSONObject().put("text", newMessage))
                })
            })

            put("contents", contentsArr)

            // Generation config
            put("generationConfig", JSONObject().apply {
                put("temperature", 0.7)
                put("topP", 0.95)
            })
        }

        val request = Request.Builder()
            .url(url)
            .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
            .build()

        val response = client.newCall(request).execute()
        if (!response.isSuccessful) return null

        val responseBody = response.body?.string() ?: return null
        val root = JSONObject(responseBody)
        val candidate = root.optJSONArray("candidates")?.optJSONObject(0)
        val text = candidate?.optJSONObject("content")?.optJSONArray("parts")?.optJSONObject(0)?.optString("text")

        if (!text.isNullOrBlank()) {
            val local = generateLocalPastoralResponse(newMessage)
            return PastoralResponse(
                text = text,
                verse1925 = local.verse1925,
                reference = local.reference,
                comfortNotes = local.comfortNotes,
                pastoralNeed = local.pastoralNeed,
                isFromGeminiApi = true
            )
        }
        return null
    }

    fun generateLocalPastoralResponse(userMessage: String): PastoralResponse {
        val lower = userMessage.lowercase()
        val matchedLeaf = THIRTEEN_CORE_LEAVES.firstOrNull { leaf ->
            leaf.keywords.any { lower.contains(it) }
        } ?: THIRTEEN_CORE_LEAVES.first()

        val verseItem: LeafVerse = matchedLeaf.verses.firstOrNull() ?: LeafVerse(
            verse1925 = "Đức Giê-hô-va là Đấng chăn giữ tôi; tôi sẽ chẳng thiếu thốn gì. Ngài khiến tôi an nghỉ nơi đồng cỏ xanh tươi, dẫn tôi đến mé nước bình tịnh.",
            reference = "Thi-thiên 23:1-2",
            comfort = "Linh hồn bạn luôn được Chúa bao bọc trong sự bình an."
        )

        val comfortText = "Hỡi con yêu dấu, Ta đã nghe tiếng con kêu cầu và thấu suốt cõi lòng con. " +
                "${matchedLeaf.direction}\n\nLời Ta phán cùng con hôm nay:"

        return PastoralResponse(
            text = comfortText,
            verse1925 = verseItem.verse1925,
            reference = verseItem.reference,
            comfortNotes = verseItem.comfort,
            pastoralNeed = matchedLeaf.title,
            isFromGeminiApi = false
        )
    }
}
