const https = require('https');

exports.handler = async (event, context) => {
  // CORS headers
  const headers = {
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Headers': 'Content-Type, Authorization',
    'Access-Control-Allow-Methods': 'GET, POST, OPTIONS',
    'Content-Type': 'application/json; charset=utf-8'
  };

  if (event.httpMethod === 'OPTIONS') {
    return {
      statusCode: 204,
      headers,
      body: ''
    };
  }

  if (event.httpMethod !== 'POST') {
    return {
      statusCode: 405,
      headers,
      body: JSON.stringify({ error: 'Method not allowed' })
    };
  }

  try {
    const payload = JSON.parse(event.body || '{}');
    const userMsg = payload.message || '';
    const history = payload.history || [];
    const customKey = payload.apiKey || '';

    const effectiveKey = (customKey.trim() || process.env.GEMINI_API_KEY || '').trim();

    if (!effectiveKey) {
      return {
        statusCode: 400,
        headers,
        body: JSON.stringify({ error: 'Chưa có Gemini API Key' })
      };
    }

    const systemPrompt = `Bạn là Đấng Lắng Nghe Nhân Từ (Tâm Sự Với Chúa) trong ứng dụng 'Cây Sự Sống'.
Hãy đồng hành, an ủi và dẫn lối người dùng theo 13 nhu cầu tâm lý & tâm linh:
1. Được yêu thương
2. Được chấp nhận
3. Được thuộc về
4. Được nhìn nhận
5. Có giá trị
6. Được an toàn
7. Có người bảo vệ
8. Được lắng nghe
9. Được thấu hiểu
10. Được nghỉ ngơi
11. Được tha thứ
12. Được phục hồi
13. Được tự do

QUY TẮC BẮT BUỘC:
- Dùng đúng hệ từ ngữ Kinh Thánh Bản Dịch 1925: 'Đức Chúa Trời', 'Đức Giê-hô-va', 'Cha về phần linh', 'Chúa Jêsus', 'Đức Thánh Linh'.
- Luôn trích dẫn ít nhất 1 câu gốc chuẩn xác từ KINH THÁNH BẢN DỊCH TRUYỀN THỐNG 1925 (tiếng Việt) kèm địa hạt sách/chương/câu.
- Giọng văn ấm áp, yêu thương, xoa dịu tổn thương, không kết án.`;

    const contents = [];
    for (const msg of history.slice(-8)) {
      contents.push({
        role: msg.role === 'user' ? 'user' : 'model',
        parts: [{ text: msg.text }]
      });
    }
    contents.push({
      role: 'user',
      parts: [{ text: userMsg }]
    });

    const geminiRequestBody = JSON.stringify({
      systemInstruction: {
        parts: [{ text: systemPrompt }]
      },
      contents: contents,
      generationConfig: {
        temperature: 0.7,
        topP: 0.95
      }
    });

    const callGemini = async (modelName) => {
      const geminiUrl = `https://generativelanguage.googleapis.com/v1beta/models/${modelName}:generateContent?key=${effectiveKey}`;
      return new Promise((resolve, reject) => {
        const req = https.request(geminiUrl, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Content-Length': Buffer.byteLength(geminiRequestBody)
          }
        }, (res) => {
          let data = '';
          res.on('data', chunk => { data += chunk; });
          res.on('end', () => resolve({ statusCode: res.statusCode, body: data }));
        });

        req.on('error', (e) => reject(e));
        req.write(geminiRequestBody);
        req.end();
      });
    };

    let result = await callGemini('gemini-1.5-flash');
    let parsed = null;
    try { parsed = JSON.parse(result.body); } catch {}

    if (!parsed?.candidates?.[0]?.content?.parts?.[0]?.text) {
      // Fallback to gemini-2.0-flash
      const fallbackResult = await callGemini('gemini-2.0-flash');
      try { parsed = JSON.parse(fallbackResult.body); } catch {}
    }

    const replyText = parsed?.candidates?.[0]?.content?.parts?.[0]?.text;

    if (replyText) {
      return {
        statusCode: 200,
        headers,
        body: JSON.stringify({ text: replyText, isFromGemini: true })
      };
    } else {
      return {
        statusCode: 200,
        headers,
        body: JSON.stringify({
          text: null,
          raw: parsed,
          error: parsed?.error?.message || 'Không nhận được câu trả lời từ Gemini'
        })
      };
    }
  } catch (err) {
    return {
      statusCode: 500,
      headers,
      body: JSON.stringify({ error: err.message || 'Server error' })
    };
  }
};
