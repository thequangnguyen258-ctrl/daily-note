package com.example.data.model

import androidx.compose.ui.graphics.Color

data class Fruit(
    val id: String,
    val name: String,
    val latin: String,
    val icon: String,
    val color: Color,
    val verse1925: String,
    val reference: String,
    val meaning: String
)

val NINE_FRUITS = listOf(
    Fruit(
        id = "love",
        name = "Yêu thương",
        latin = "Agapē",
        icon = "❤️",
        color = Color(0xFFFF6B81),
        verse1925 = "Đức Chúa Trời là sự yêu thương, ai ở trong sự yêu thương, là ở trong Đức Chúa Trời, và Đức Chúa Trời ở trong người ấy.",
        reference = "I Giăng 4:16b",
        meaning = "Tình yêu thương vô điều kiện, bao dung và nâng đỡ tha nhân."
    ),
    Fruit(
        id = "joy",
        name = "Vui mừng",
        latin = "Chara",
        icon = "✨",
        color = Color(0xFFFFD152),
        verse1925 = "Hãy vui mừng trong Chúa luôn luôn. Tôi lại còn nói nữa: hãy vui mừng đi!",
        reference = "Phi-líp 4:4",
        meaning = "Sự vui mừng sâu xa từ nơi Chúa, không phụ thuộc vào nghịch cảnh bên ngoài."
    ),
    Fruit(
        id = "peace",
        name = "Bình an",
        latin = "Eirēnē",
        icon = "🕊️",
        color = Color(0xFF2ED573),
        verse1925 = "Ta để sự bình an lại cho các ngươi; ta ban sự bình an ta cho các ngươi; ta cho các ngươi sự bình an chẳng phải như thế gian cho. Lòng các ngươi chớ bối rối và đừng sợ hãi.",
        reference = "Giăng 14:27",
        meaning = "Sự bình an siêu phàm gìn giữ lòng và ý tưởng trong mọi phong ba."
    ),
    Fruit(
        id = "patience",
        name = "Nhịn nhục",
        latin = "Makrothumia",
        icon = "⏳",
        color = Color(0xFFA55EEA),
        verse1925 = "Nhưng ai trông đợi Đức Giê-hô-va thì chắc được sức mới, cất cánh bay cao như chim ưng; chạy mà không mệt nhọc, đi mà không mòn mỏi.",
        reference = "Ê-sai 40:31",
        meaning = "Sự kiên nhẫn bền đỗ và trông cậy thời điểm hoàn hảo của Chúa."
    ),
    Fruit(
        id = "kindness",
        name = "Nhân từ",
        latin = "Chrēstotēs",
        icon = "🌿",
        color = Color(0xFFFF9F43),
        verse1925 = "Hãy ở với nhau cách nhân từ, đầy dẫy lòng thương xót, tha thứ nhau như Đức Chúa Trời đã tha thứ anh em trong Đấng Christ vậy.",
        reference = "Ê-phê-sô 4:32",
        meaning = "Tấm lòng ấm áp, sẵn sàng thấu hiểu và sẻ chia xoa dịu vết thương."
    ),
    Fruit(
        id = "goodness",
        name = "Hiền lành",
        latin = "Agathōsunē",
        icon = "🌱",
        color = Color(0xFF1DD1A1),
        verse1925 = "Sự sáng các ngươi hãy soi trước mặt người ta như vậy, đặng họ thấy những việc lành của các ngươi, và ngợi khen Cha các ngươi ở trên trời.",
        reference = "Ma-thi-ơ 5:16",
        meaning = "Lối sống thuần khiết, làm điều lành và mang lại sự sống cho mọi người."
    ),
    Fruit(
        id = "faithfulness",
        name = "Trung tín",
        latin = "Pistis",
        icon = "⚓",
        color = Color(0xFF48DBFB),
        verse1925 = "Hỡi đầy tớ ngay lành trung tín kia, được lắm; ngươi đã trung tín trong việc nhỏ, ta sẽ lập ngươi coi sóc nhiều việc; hãy đến hưởng sự vui mừng của Chúa ngươi.",
        reference = "Ma-thi-ơ 25:21",
        meaning = "Lòng sắt son không đổi dời, kiên định nơi lời hứa của Chúa."
    ),
    Fruit(
        id = "gentleness",
        name = "Mềm mại",
        latin = "Prautēs",
        icon = "🌸",
        color = Color(0xFFFAB1A0),
        verse1925 = "Phước cho những kẻ nhu mì, vì sẽ hưởng được đất!",
        reference = "Ma-thi-ơ 5:5",
        meaning = "Sự khiêm nhường, không nóng giận, lời đáp êm dịu làm nguôi cơn giận."
    ),
    Fruit(
        id = "selfcontrol",
        name = "Tiết độ",
        latin = "Enkrateia",
        icon = "🛡️",
        color = Color(0xFF00D2D3),
        verse1925 = "Kẻ nào chậm nóng giận thắng hơn người dõng sĩ; và ai cai trị lòng mình cốt hơn kẻ chiếm lấy thành.",
        reference = "Châm-ngôn 16:32",
        meaning = "Làm chủ cảm xúc, ước muốn và hướng trọn tâm trí về sự công chính."
    )
)

data class LeafVerse(
    val verse1925: String,
    val reference: String,
    val comfort: String
)

data class CoreNeedLeaf(
    val id: String,
    val title: String,
    val icon: String,
    val wound: String,
    val belief: String,
    val pattern: String,
    val direction: String,
    val keywords: List<String>,
    val verses: List<LeafVerse>
)

val THIRTEEN_CORE_LEAVES = listOf(
    CoreNeedLeaf(
        id = "need_loved",
        title = "Được Yêu Thương",
        icon = "❤️",
        wound = "Bị bỏ bê, ít được thể hiện tình cảm hoặc chỉ được yêu khi đáp ứng kỳ vọng.",
        belief = "“Mình phải làm gì đó mới đáng được yêu.”",
        pattern = "Có xu hướng làm hài lòng người khác, tìm kiếm sự công nhận bên ngoài.",
        direction = "Tình yêu đi trước mọi hành vi của con người (Tình yêu vô điều kiện của Đức Chúa Trời).",
        keywords = listOf("yêu", "tình yêu", "bỏ rơi", "bỏ bê", "làm hài lòng", "công nhận", "xứng đáng", "tình cảm", "thiếu thốn"),
        verses = listOf(
            LeafVerse(
                verse1925 = "Nhưng Đức Chúa Trời tỏ lòng yêu thương Ngài đối với chúng ta, khi chúng ta còn là người có tội, thì Đấng Christ vì chúng ta chịu chết.",
                reference = "Rô-ma 5:8",
                comfort = "Chúa đã yêu bạn bằng tình yêu vô điều kiện trước khi bạn làm được bất cứ điều gì."
            ),
            LeafVerse(
                verse1925 = "Lòng Đức Chúa Trời yêu chúng ta đã bày tỏ ra trong điều nầy: Đức Chúa Trời đã sai Con một Ngài đến thế gian, đặng chúng ta nhờ Con ấy mà được sống.",
                reference = "I Giăng 4:9",
                comfort = "Tình yêu của Ngài là khởi nguồn và là bến đỗ bình an trọn vẹn nhất cho bạn."
            )
        )
    ),
    CoreNeedLeaf(
        id = "need_accepted",
        title = "Được Chấp Nhận",
        icon = "🤝",
        wound = "Bị từ chối, chế giễu hoặc thường xuyên bị so sánh với người khác.",
        belief = "“Nếu người khác biết con người thật của mình, họ sẽ bỏ mình.”",
        pattern = "Che giấu bản thân, đeo mặt nạ, people-pleasing, giả vờ hoàn hảo.",
        direction = "Được tiếp nhận không phải vì mình hoàn hảo mà bởi ân điển nhưng không.",
        keywords = listOf("chấp nhận", "từ chối", "chế giễu", "so sánh", "che giấu", "giả vờ", "mặt nạ", "tự ti", "bị chê"),
        verses = listOf(
            LeafVerse(
                verse1925 = "Vậy thì, anh em hãy tiếp nhận nhau, cũng như Đấng Christ đã tiếp nhận anh em, để Đức Chúa Trời được vinh hiển.",
                reference = "Rô-ma 15:7",
                comfort = "Đấng Christ đã tiếp nhận trọn vẹn con người thật của bạn vào lòng Ngài."
            ),
            LeafVerse(
                verse1925 = "Bởi sự thương yêu của Ngài đã định trước cho chúng ta được trở nên con nuôi Ngài bởi Đức Chúa Jêsus Christ, theo ý tốt của Ngài.",
                reference = "Ê-phê-sô 1:5",
                comfort = "Bạn được Chúa nhận làm con yêu dấu bởi ân điển nhưng không, không cần phải giả vờ hoàn hảo."
            )
        )
    ),
    CoreNeedLeaf(
        id = "need_belonging",
        title = "Được Thuộc Về",
        icon = "🏡",
        wound = "Cô lập, bị nhóm bạn loại trừ hoặc gia đình thiếu sự gắn kết ấm áp.",
        belief = "“Mình không thuộc về đâu cả, mình lạc lõng.”",
        pattern = "Bám víu quá mức vào một người hoặc ngược lại tự cô lập chính mình.",
        direction = "Cảm nhận mình thuộc về gia đình đời đời của Đức Chúa Trời.",
        keywords = listOf("thuộc về", "cô lập", "lạc lõng", "loại trừ", "xa cách", "bơ vơ", "một mình", "không gia đình"),
        verses = listOf(
            LeafVerse(
                verse1925 = "Dường ấy anh em chẳng phải là người ngoại, cũng chẳng phải là kẻ ở trọ nữa, nhưng là người đồng dân với các thánh đồ, và là người nhà của Đức Chúa Trời.",
                reference = "Ê-phê-sô 2:19",
                comfort = "Bạn không phải kẻ ở trọ hay người ngoài; bạn là thành viên quý báu trong nhà Chúa."
            ),
            LeafVerse(
                verse1925 = "Nhưng anh em là dòng giống được lựa chọn, là chức thầy tế lễ nhà vua, là dân thánh, là dân thuộc về Đức Chúa Trời.",
                reference = "I Phi-e-rơ 2:9",
                comfort = "Bạn đã được Chúa chọn và thuộc trọn về vương quốc vinh hiển của Ngài."
            )
        )
    ),
    CoreNeedLeaf(
        id = "need_seen",
        title = "Được Nhìn Nhận",
        icon = "👁️",
        wound = "Bị phớt lờ, tiếng nói không được coi trọng trong quá khứ.",
        belief = "“Mình không quan trọng, không ai nhìn thấy mình.”",
        pattern = "Cố gây chú ý quá mức hoặc hoàn toàn thu mình, bất cần.",
        direction = "Đức Chúa Trời biết và nhìn thấy tỏ tường con người thật cùng giá trị của mình.",
        keywords = listOf("nhìn nhận", "phớt lờ", "không quan trọng", "coi thường", "vô hình", "gây chú ý", "thu mình"),
        verses = listOf(
            LeafVerse(
                verse1925 = "Hỡi Đức Giê-hô-va, Ngài đã dò xét tôi, và biết tôi. Khi tôi ngồi, lúc tôi đứng dậy, Chúa đều biết cả; từ xa Chúa hiểu biết ý tưởng tôi.",
                reference = "Thi-thiên 139:1-2",
                comfort = "Chúa thấu suốt mọi hành trình và nhìn thấy từng nỗi lòng thầm kín nhất của bạn."
            ),
            LeafVerse(
                verse1925 = "Đừng sợ chi, vì ta đã chuộc ngươi; ta đã lấy tên ngươi gọi ngươi; ngươi thuộc về ta... Vì ngươi là quí báu trước mắt ta, đẹp đẽ và ta đã yêu ngươi.",
                reference = "Ê-sai 43:1, 4",
                comfort = "Bạn luôn là người quý báu, đẹp đẽ và được trân trọng tuyệt đối trước mắt Ngài."
            )
        )
    ),
    CoreNeedLeaf(
        id = "need_valued",
        title = "Có Giá Trị",
        icon = "💎",
        wound = "Thường xuyên bị so sánh, tình yêu gắn với thành tích hoặc từng trải qua thất bại.",
        belief = "“Giá trị của mình = thành tích và sự thành công.”",
        pattern = "Chủ nghĩa hoàn hảo hóa, ganh đua so sánh, hoặc tự khinh chê bản thân.",
        direction = "Giá trị của con người có trước mọi thành tích, do chính Chúa tạo dựng.",
        keywords = listOf("giá trị", "thành tích", "điểm số", "kém cỏi", "thua kém", "thất bại", "tự khinh", "hoàn hảo", "ghen tỵ", "so sánh", "giàu hơn"),
        verses = listOf(
            LeafVerse(
                verse1925 = "Vì chúng ta là việc Ngài làm ra, đã được dựng nên trong Đức Chúa Jêsus Christ để làm việc lành mà Đức Chúa Trời đã sắm sẵn trước cho chúng ta làm theo.",
                reference = "Ê-phê-sô 2:10",
                comfort = "Bạn là kiệt tác nghệ thuật tuyệt hảo được chính bàn tay Chúa tạo tác."
            ),
            LeafVerse(
                verse1925 = "Lòng bình tịnh là sự sống của thân thể; song sự ghen ghét là đồ mục của xương cốt.",
                reference = "Châm-ngôn 14:30",
                comfort = "Đừng để lòng mình mệt mỏi vì so sánh; Chúa ban cho bạn một lối đi riêng đầy phước hạnh."
            )
        )
    ),
    CoreNeedLeaf(
        id = "need_safe",
        title = "Được An Toàn",
        icon = "🛡️",
        wound = "Trải qua bạo lực, môi trường bất ổn hoặc người chăm sóc có phản ứng khó đoán.",
        belief = "“Thế giới này không an toàn, nguy hiểm rình rập.”",
        pattern = "Luôn cảnh giác cao độ, muốn kiểm soát mọi thứ xung quanh hoặc né tránh.",
        direction = "Sự khác biệt giữa kiểm soát và nương náu dưới bóng toàn năng của Chúa.",
        keywords = listOf("an toàn", "bất an", "nguy hiểm", "kiểm soát", "cảnh giác", "lo sợ", "hoảng loạn", "bạo lực", "stress"),
        verses = listOf(
            LeafVerse(
                verse1925 = "Hỡi Đức Giê-hô-va, tôi sẽ nằm và ngủ bình an; vì chỉ một mình Ngài làm cho tôi được ở yên ổn.",
                reference = "Thi-thiên 4:8",
                comfort = "Chỉ duy nơi Chúa mới có sự yên ổn và bình an đích thực bảo bọc giấc ngủ bạn."
            ),
            LeafVerse(
                verse1925 = "Người nào ở nơi kín đáo của Đấng Chí Cao, sẽ được hằng ở dưới bóng của Đấng Toàn Năng... Ngài sẽ lấy lông Ngài mà che chở ngươi, và dưới cánh Ngài, ngươi sẽ được nương náu mình.",
                reference = "Thi-thiên 91:1, 4",
                comfort = "Hãy nương náu dưới bóng cánh Đấng Toàn Năng thay vì cố gắng gồng mình kiểm soát."
            )
        )
    ),
    CoreNeedLeaf(
        id = "need_protected",
        title = "Có Người Bảo Vệ",
        icon = "🏰",
        wound = "Phải trưởng thành quá sớm, thiếu người nâng đỡ, gánh vác một mình.",
        belief = "“Mình phải tự lo tất cả, không ai bảo vệ che chở cho mình.”",
        pattern = "Không dám nhờ giúp đỡ, luôn cố gồng gánh tự giải quyết mọi chuyện.",
        direction = "Cho phép bản thân yếu đuối và tìm nơi nương náu vững bền nơi Chúa.",
        keywords = listOf("bảo vệ", "tự lo", "gồng gánh", "trưởng thành sớm", "cô độc", "không ai giúp", "nương náu", "che chở"),
        verses = listOf(
            LeafVerse(
                verse1925 = "Đức Chúa Trời là nơi nương náu và sức lực của chúng tôi, Ngài sẵn giúp đỡ trong cơn gian truân.",
                reference = "Thi-thiên 46:1",
                comfort = "Bạn được phép yếu đuối trước mặt Chúa, vì Ngài chính là đồn lũy bảo vệ bạn."
            ),
            LeafVerse(
                verse1925 = "Sự tiếp trợ tôi đến từ Đức Giê-hô-va, là Đấng dựng nên trời và đất... Đức Giê-hô-va sẽ gìn giữ ngươi khỏi mọi tai họa; Ngài sẽ gìn giữ linh hồn ngươi.",
                reference = "Thi-thiên 121:2, 7",
                comfort = "Đấng gìn giữ bạn chẳng hề nhắp mắt hay ngủ say; hãy trao trọn sự bảo vệ cho Ngài."
            )
        )
    ),
    CoreNeedLeaf(
        id = "need_listened",
        title = "Được Lắng Nghe",
        icon = "👂",
        wound = "Cảm xúc thường bị phủ nhận như “đừng khóc”, “có gì đâu mà buồn”, “chuyện nhỏ”.",
        belief = "“Cảm xúc của mình không quan trọng, nói ra cũng vô ích.”",
        pattern = "Đè nén, kìm nén, né tránh hoặc không biết cách gọi tên cảm xúc thật.",
        direction = "Đức Chúa Trời trân quý và mời gọi con người giãi bày, dốc đổ lòng mình.",
        keywords = listOf("lắng nghe", "đè nén", "phủ nhận", "đừng khóc", "kìm nén", "giãi bày", "không ai nghe", "im lặng", "buồn phiền"),
        verses = listOf(
            LeafVerse(
                verse1925 = "Mắt Đức Giê-hô-va đoái xem người công bình, lỗ tai Ngài lắng nghe tiếng kêu cầu của họ... Đức Giê-hô-va ở gần những người có lòng đau thương, và cứu vớt kẻ có tâm thần thống hối.",
                reference = "Thi-thiên 34:15, 18",
                comfort = "Tai Chúa hằng lắng nghe từng tiếng thở dài và giọt nước mắt thầm kín của bạn."
            ),
            LeafVerse(
                verse1925 = "Hỡi cả dân sự, hãy nhờ cậy nơi Ngài luôn luôn, hãy dốc đổ sự lòng mình ra tại trước mặt Ngài: Đức Chúa Trời là nơi nương náu của chúng ta.",
                reference = "Thi-thiên 62:8",
                comfort = "Hãy tự do dốc trọn mọi nỗi niềm trong lòng ra trước mặt Chúa; Ngài thấu cảm tất cả."
            )
        )
    ),
    CoreNeedLeaf(
        id = "need_understood",
        title = "Được Thấu Hiểu",
        icon = "🕊️",
        wound = "Thường xuyên bị hiểu lầm, phán xét hoặc áp đặt định kiến.",
        belief = "“Không ai thực sự hiểu mình, chia sẻ ra chỉ thêm tổn thương.”",
        pattern = "Không chia sẻ, khép kín, giữ kín mọi suy nghĩ và nỗi đau trong lòng.",
        direction = "Một Đấng thấu hiểu tận cùng lòng dạ, cả những điều chưa thể thốt nên lời.",
        keywords = listOf("thấu hiểu", "hiểu lầm", "phán xét", "khép kín", "giấu kín", "không ai hiểu", "cô đơn trong lòng", "bị trách"),
        verses = listOf(
            LeafVerse(
                verse1925 = "Chúa biết ý tưởng tôi từ đằng xa... Vì lời chưa ở trên lưỡi tôi, kìa, hỡi Đức Giê-hô-va, Ngài đã biết trọn hết rồi.",
                reference = "Thi-thiên 139:2, 4",
                comfort = "Ngay cả khi bạn chưa biết cất lời ra sao, Chúa đã thấu hiểu tường tận cõi lòng bạn."
            ),
            LeafVerse(
                verse1925 = "Vì chúng ta chẳng phải có thầy tế lễ thượng phẩm chẳng có thể cảm thương sự yếu đuối chúng ta, bèn là Đấng đã từng bị thử thách trong mọi việc cũng như chúng ta, song chẳng phạm tội.",
                reference = "Hê-bơ-rơ 4:15",
                comfort = "Chúa Jêsus đã trải qua mọi thử thách trên đất này để có thể cảm thương và đồng hành cùng bạn."
            )
        )
    ),
    CoreNeedLeaf(
        id = "need_rest",
        title = "Được Nghỉ Ngơi",
        icon = "🌿",
        wound = "Quá tải, gánh trách nhiệm quá mức hoặc luôn phải tỏ ra mạnh mẽ.",
        belief = "“Nếu mình dừng lại, mọi thứ sẽ sụp đổ; mình không được phép nghỉ.”",
        pattern = "Làm việc quá mức, khó thả lỏng, ám ảnh năng suất, dễ kiệt sức.",
        direction = "Giá trị bản thân không phụ thuộc vào năng suất; sự yên nghỉ thánh thiện trong Chúa.",
        keywords = listOf("nghỉ ngơi", "kiệt sức", "quá tải", "gánh nặng", "sụp đổ", "áp lực", "mệt mỏi", "đuối sức", "công việc", "deadline"),
        verses = listOf(
            LeafVerse(
                verse1925 = "Hỡi những kẻ mệt mỏi và gánh nặng, hãy đến cùng ta, ta sẽ cho các ngươi được yên nghỉ. Ta có lòng nhu mì, khiêm nhường; nên hãy gánh lấy ách của ta, và học theo ta; thì linh hồn các ngươi sẽ được yên nghỉ.",
                reference = "Ma-thi-ơ 11:28-29",
                comfort = "Hãy hạ bớt những gánh nặng tự mang; Chúa trao cho bạn sự yên nghỉ thanh thản."
            ),
            LeafVerse(
                verse1925 = "Đức Giê-hô-va là Đấng chăn giữ tôi: tôi sẽ chẳng thiếu thốn gì. Ngài khiến tôi an nghỉ nơi đồng cỏ xanh tươi, dẫn tôi đến mé nước bình tịnh. Ngài bổ lại linh hồn tôi.",
                reference = "Thi-thiên 23:1-3",
                comfort = "Linh hồn bạn xứng đáng được nghỉ ngơi bên mé nước bình tịnh của Đức Chúa Trời."
            )
        )
    ),
    CoreNeedLeaf(
        id = "need_forgiven",
        title = "Được Tha Thứ",
        icon = "🕊️",
        wound = "Từng mắc sai lầm, phạm tội hoặc mang cảm giác xấu hổ, tội lỗi sâu sắc.",
        belief = "“Mình là người xấu, không thể tha thứ, không xứng đáng được hạnh phúc.”",
        pattern = "Trốn tránh, che giấu, dằn vặt tự trách hoặc tự trừng phạt bản thân.",
        direction = "Sự khác biệt giữa tội lỗi và sự kết án; huyết Chúa tha thứ và tẩy sạch hoàn toàn.",
        keywords = listOf("tha thứ", "sai lầm", "tội lỗi", "xấu hổ", "dằn vặt", "tự trách", "tự trừng phạt", "hối hận", "cắn rứt"),
        verses = listOf(
            LeafVerse(
                verse1925 = "Còn nếu chúng ta xưng tội mình, thì Ngài là thành tín công bình để tha tội cho chúng ta, và làm cho chúng ta sạch mọi điều gian ác.",
                reference = "I Giăng 1:9",
                comfort = "Ân điển tha thứ của Chúa xóa sạch mọi vết nhơ và cho bạn một lương tâm trong sạch."
            ),
            LeafVerse(
                verse1925 = "Phước thay cho người nào được tha sự vi phạm mình, được khỏa lấp tội lỗi mình!... Tôi đã thú tội cùng Chúa, không giấu gian ác tôi... Chúa bèn tha tội ác tôi.",
                reference = "Thi-thiên 32:1, 5",
                comfort = "Sự tha thứ từ Chúa giải phóng bạn khỏi mọi sự dằn vặt và xiềng xích xấu hổ."
            )
        )
    ),
    CoreNeedLeaf(
        id = "need_restored",
        title = "Được Phục Hồi",
        icon = "🌱",
        wound = "Thất bại nặng nề, sa ngã hoặc mất phương hướng hoàn toàn.",
        belief = "“Mình đã hỏng rồi, không thể làm lại, cuộc đời coi như xong.”",
        pattern = "Buông xuôi, mất hy vọng, chán chường, không muốn bắt đầu lại.",
        direction = "Thất bại không phải là điểm kết thúc; Chúa là Đấng tái tạo và bù đắp bội phần.",
        keywords = listOf("phục hồi", "thất bại", "hỏng", "buông xuôi", "mất hy vọng", "làm lại", "tái sinh", "sa ngã", "bế tắc"),
        verses = listOf(
            LeafVerse(
                verse1925 = "Đức Chúa Trời ôi! xin hãy dựng nên trong tôi một lòng trong sạch, và làm cho mới lại trong tôi một thần linh ngay thẳng... Xin hãy ban lại cho tôi sự vui mừng về sự cứu rỗi của Chúa.",
                reference = "Thi-thiên 51:10, 12",
                comfort = "Chúa có quyền năng tái tạo một tâm linh hoàn toàn mới và tươi sáng trong bạn."
            ),
            LeafVerse(
                verse1925 = "Ta sẽ đền bù lại cho các ngươi những năm mà cào cào, sâu lột vỏ, sâu cắn lá và sâu keo đã cắn phá.",
                reference = "Giô-ên 2:25",
                comfort = "Những mất mát đau thương trong quá khứ sẽ được Chúa đền bù và chúc phước bội phần."
            )
        )
    ),
    CoreNeedLeaf(
        id = "need_free",
        title = "Được Tự Do",
        icon = "🕊️",
        wound = "Bị kiểm soát ngột ngạt, chịu áp lực định kiến hoặc phải sống theo mong muốn của người khác.",
        belief = "“Mình không được phép là chính mình, luôn bị trói buộc.”",
        pattern = "Nổi loạn cực đoan hoặc ngược lại phục tùng tuyệt đối trong cay đắng.",
        direction = "Sự tự do thật trong Chúa (không đồng nghĩa với phóng túng hay vô trách nhiệm).",
        keywords = listOf("tự do", "kiểm soát", "áp lực", "trói buộc", "ngột ngạt", "áp đặt", "nổi loạn", "khuôn mẫu", "bức bối"),
        verses = listOf(
            LeafVerse(
                verse1925 = "Đấng Christ đã buông tha chúng ta cho được tự do; vậy hãy đứng vững, chớ lại để mình dưới ách tôi mọi nữa... Hỡi anh em, anh em đã được gọi đến sự tự do.",
                reference = "Ga-la-ti 5:1, 13",
                comfort = "Trong Chúa, bạn được giải phóng để sống bình an là chính mình trong tình yêu thương."
            ),
            LeafVerse(
                verse1925 = "Chúa tức là Thánh Linh, nơi nào có Thánh Linh của Chúa, nơi đó có sự tự do.",
                reference = "II Cô-rinh-tô 3:17",
                comfort = "Thánh Linh Chúa mang lại cho bạn sự tự do và sự sống đích thực trong tâm hồn."
            )
        )
    )
)

data class FaithRoot(
    val id: String,
    val theme: String,
    val reference: String,
    val verse1925: String
)

val SEVEN_FAITH_ROOTS = listOf(
    FaithRoot("root_1", "Vầng Đá Vững Chắc", "Thi-thiên 18:2", "Đức Giê-hô-va là vầng đá của tôi, đồn lũy tôi, Đấng giải cứu tôi; Đức Chúa Trời là vầng đá tôi, nơi tôi sẽ tìm được sự nương náu mình; Ngài là cái khiên của tôi, sừng cứu rỗi tôi, ngọn tháp cao của tôi."),
    FaithRoot("root_2", "Bám Rễ Trong Chúa", "Cô-lô-se 2:7", "Châm rễ và lập nền trong Ngài, lấy đức tin làm cho bền vững, tùy theo điều anh em đã được dạy dỗ, và hãy dư dật trong sự cảm tạ."),
    FaithRoot("root_3", "Cây Trồng Gần Dòng Nước", "Thi-thiên 1:3", "Người ấy sẽ như cây trồng gần dòng nước, sanh bông trái theo thì tiết, lá nó cũng chẳng tàn héo; mọi sự người làm đều sẽ thạnh vượng."),
    FaithRoot("root_4", "Hết Lòng Tin Cậy", "Châm-ngôn 3:5-6", "Hãy hết lòng tin cậy Đức Giê-hô-va, chớ nương cậy nơi sự thông sáng của con; phàm trong các việc làm của con, khá nhận biết Ngài, thì Ngài sẽ chỉ dẫn các nẻo của con."),
    FaithRoot("root_5", "Nơi Nương Náu Sẵn Có", "Thi-thiên 46:1", "Đức Chúa Trời là nơi nương náu và sức lực của chúng tôi, Ngài hằng giúp đỡ trong cơn hoạn nạn."),
    FaithRoot("root_6", "Đừng Sợ Hãi", "Ê-sai 41:10", "Đừng sợ, vì ta ở với ngươi; chớ kinh khiếp, vì ta là Đức Chúa Trời ngươi. Ta sẽ bổ sức cho ngươi; phải, ta sẽ giúp đỡ ngươi, lấy tay hữu công bình ta mà nâng đỡ ngươi."),
    FaithRoot("root_7", "Bình Tịnh & An Nghỉ", "Thi-thiên 23:1-2", "Đức Giê-hô-va là Đấng chăn giữ tôi; tôi sẽ chẳng thiếu thốn gì. Ngài khiến tôi an nghỉ nơi đồng cỏ xanh tươi, dẫn tôi đến mé nước bình tịnh.")
)

data class UserAccount(
    val username: String,
    var password: String,
    var fullName: String,
    val isAdmin: Boolean = false
)

data class MoodItem(
    val id: String,
    val label: String,
    val icon: String,
    val color: Color
)

val MOOD_ITEMS = listOf(
    MoodItem("cam_ta", "Cảm tạ", "🙏", Color(0xFFF59E0B)),
    MoodItem("binh_an", "Bình an", "🕊️", Color(0xFF10B981)),
    MoodItem("vui_mung", "Vui mừng", "✨", Color(0xFFEAB308)),
    MoodItem("hanh_phuc", "Hạnh phúc", "🥰", Color(0xFFEC4899)),
    MoodItem("hy_vong", "Hy vọng", "🌱", Color(0xFF3B82F6)),
    MoodItem("thoa_long", "Thỏa lòng", "🌸", Color(0xFF14B8A6)),
    MoodItem("kho_khan_met_moi", "Khó khăn mệt mỏi", "🥀", Color(0xFF94A3B8)),
    MoodItem("lo_au_bat_an", "Lo âu & Bất an", "🌧️", Color(0xFF8B5CF6)),
    MoodItem("dau_buon_ton_thuong", "Đau buồn & Tổn thương", "💧", Color(0xFF60A5FA)),
    MoodItem("ghen_ty_so_sanh", "Ghen tỵ & So sánh", "⚖️", Color(0xFFF97316)),
    MoodItem("tuc_gian_buc_boi", "Tức giận & Bực bội", "🔥", Color(0xFFEF4444)),
    MoodItem("boi_roi_lac_loi", "Bối rối & Lạc lối", "🧭", Color(0xFFA855F7)),
    MoodItem("co_don", "Cô đơn & Trống vắng", "🍂", Color(0xFF64748B)),
    MoodItem("so_hai_hoang_mang", "Sợ hãi & Hoang mang", "⚡", Color(0xFFD946EF))
)

data class JournalEntry(
    val id: String,
    val title: String,
    val content: String,
    val moods: List<String>,
    val category: String,
    val associatedFruit: String,
    val createdAt: Long = System.currentTimeMillis()
)

data class PrayerMessage(
    val id: String,
    val sender: String, // "user" or "god_ai"
    val text: String,
    val verse1925: String? = null,
    val reference: String? = null,
    val associatedFruit: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)

data class HomeMessage(
    val recentProblem: String,
    val verse1925: String,
    val reference: String,
    val blessing: String,
    val comfortText: String,
    val updatedAt: Long = System.currentTimeMillis()
)

data class TreeState(
    val waterDrops: Int = 250,
    val rootsCount: Int = 3,
    val leavesCount: Int = 8,
    val branchesCount: Int = 2,
    val unlockedFruits: List<String> = listOf("love", "peace", "joy"),
    val level: Int = 1,
    val isWilted: Boolean = false,
    val lastWatered: Long = System.currentTimeMillis()
)

data class HeartFieldState(
    val isNegative: Boolean = false,
    val negativityType: String = "",
    val thornsLevel: Int = 0,
    val amenLeaves: List<String> = emptyList(),
    val amenFruits: List<String> = emptyList(),
    val amenRoots: List<String> = emptyList(),
    val lastHealedAt: Long? = null
)

data class FruitMemory(
    val id: String,
    val fruitId: String,
    val text: String,
    val date: Long = System.currentTimeMillis()
)

data class AppSettings(
    val soundEnabled: Boolean = true,
    val ambientTrack: String = "miracle_528",
    val customAudioUrl: String = "",
    val geminiApiKey: String = "",
    val userName: String = "Bạn nhỏ"
)
