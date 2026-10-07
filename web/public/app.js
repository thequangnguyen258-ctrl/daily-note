// CÂY SỰ SỐNG - WEB APP (PWA & MOBILE READY)
// Pure Vanilla JS - Zero External Dependency

// 1. DATA CONSTANTS
const SPIRIT_FRUITS = [
  { id: 'love', name: 'Yêu Thương', meaning: 'Tình yêu thương xuất phát từ Chúa, không điều kiện, bao dung và nâng đỡ.', scripture: 'Đức Chúa Trời là sự yêu thương; ai ở trong sự yêu thương, là ở trong Đức Chúa Trời.', ref: '1 Giăng 4:16', color: '#EF4444', x: 0.38, y: 0.30 },
  { id: 'joy', name: 'Vui Mừng', meaning: 'Niềm vui sâu xa trong Thánh Linh không phụ thuộc vào hoàn cảnh bên ngoài.', scripture: 'Sự vui mừng của Đức Giê-hô-va là sức mạnh của anh em.', ref: 'Nê-hê-mi 8:10', color: '#F59E0B', x: 0.50, y: 0.22 },
  { id: 'peace', name: 'Bình An', meaning: 'Sự bình an siêu nhiên vượt quá mọi sự hiểu biết của tâm trí phàm nhân.', scripture: 'Ta để sự bình an lại cho các ngươi; Ta ban sự bình an của Ta cho các ngươi.', ref: 'Giăng 14:27', color: '#10B981', x: 0.62, y: 0.30 },
  { id: 'patience', name: 'Nhịn Nhục', meaning: 'Kiên trì chịu đựng trong hy vọng, không nóng nảy, biết trông cậy thời điểm của Ngài.', scripture: 'Hãy yên lặng trước mặt Đức Giê-hô-va và kiên nhẫn chờ đợi Ngài.', ref: 'Thi-thiên 37:7', color: '#8B5CF6', x: 0.28, y: 0.38 },
  { id: 'kindness', name: 'Nhân Từ', meaning: 'Lòng trắc ẩn biết sẻ chia, quan tâm đến những tâm hồn bị tổn thương.', scripture: 'Hãy ở với nhau cách nhân từ, đầy lòng thương xót, tha thứ nhau.', ref: 'Ê-phê-sô 4:32', color: '#EC4899', x: 0.72, y: 0.38 },
  { id: 'goodness', name: 'Hiền Lành', meaning: 'Bản tính ngay thẳng, làm điều thiện lành xuất phát từ tấm lòng thanh sạch.', scripture: 'Hãy làm việc lành, giàu lòng bố thí, sẵn sàng chia sẻ.', ref: '1 Ti-mô-thê 6:18', color: '#06B6D4', x: 0.42, y: 0.42 },
  { id: 'faithfulness', name: 'Trung Tín', meaning: 'Vững vàng son sắt trong giao ước, không dao động trước cám dỗ hay thử thách.', scripture: 'Hãy trung tín cho đến chết, rồi Ta sẽ ban cho ngươi mão triều thiên của sự sống.', ref: 'Khải-huyền 2:10', color: '#3B82F6', x: 0.58, y: 0.42 },
  { id: 'gentleness', name: 'Mềm Mại', meaning: 'Sự khiêm nhường hạ mình, không kiêu căng tranh cạnh, nhu mì trước Chúa.', scripture: 'Hỡi những kẻ mệt mỏi và gánh nặng, hãy đến cùng Ta... vì Ta có lòng nhu mì, khiêm nhường.', ref: 'Ma-thi-ơ 11:28-29', color: '#14B8A6', x: 0.33, y: 0.48 },
  { id: 'selfcontrol', name: 'Tiết Độ', meaning: 'Làm chủ cảm xúc, kỷ luật bản thân, giữ lòng trong sạch trước Chúa.', scripture: 'Người nào không kiềm chế được lòng mình như một cái thành bị phá vỡ không có tường lũy.', ref: 'Châm-ngôn 25:28', color: '#D97706', x: 0.67, y: 0.48 }
];

const CORE_NEEDS = [
  {
    id: 'need_loved',
    label: 'Được Yêu Thương',
    icon: '❤️',
    wound: 'Bị bỏ bê, ít được thể hiện tình cảm hoặc chỉ được yêu khi đáp ứng kỳ vọng.',
    belief: '“Mình phải làm gì đó mới đáng được yêu.”',
    pattern: 'Có xu hướng làm hài lòng người khác, tìm kiếm sự công nhận bên ngoài.',
    direction: 'Tình yêu đi trước mọi hành vi của con người (Tình yêu vô điều kiện của Đức Chúa Trời).',
    scripture: 'Nhưng Đức Chúa Trời tỏ lòng yêu thương Ngài đối với chúng ta, khi chúng ta còn là người có tội, thì Đấng Christ vì chúng ta chịu chết.',
    ref: 'Rô-ma 5:8',
    comfort: 'Chúa đã yêu bạn bằng tình yêu vô điều kiện trước khi bạn làm được bất cứ điều gì.',
    x: 0.22, y: 0.33
  },
  {
    id: 'need_accepted',
    label: 'Được Chấp Nhận',
    icon: '🤝',
    wound: 'Bị từ chối, chế giễu hoặc thường xuyên bị so sánh với người khác.',
    belief: '“Nếu người khác biết con người thật của mình, họ sẽ bỏ mình.”',
    pattern: 'Che giấu bản thân, đeo mặt nạ, people-pleasing, giả vờ hoàn hảo.',
    direction: 'Được tiếp nhận không phải vì mình hoàn hảo mà bởi ân điển nhưng không.',
    scripture: 'Vậy thì, anh em hãy tiếp nhận nhau, cũng như Đấng Christ đã tiếp nhận anh em, để Đức Chúa Trời được vinh hiển.',
    ref: 'Rô-ma 15:7',
    comfort: 'Đấng Christ đã tiếp nhận trọn vẹn con người thật của bạn vào lòng Ngài.',
    x: 0.78, y: 0.33
  },
  {
    id: 'need_belonging',
    label: 'Được Thuộc Về',
    icon: '🏡',
    wound: 'Cô lập, bị nhóm bạn loại trừ hoặc gia đình thiếu sự gắn kết ấm áp.',
    belief: '“Mình không thuộc về đâu cả, mình lạc lõng.”',
    pattern: 'Bám víu quá mức vào một người hoặc ngược lại tự cô lập chính mình.',
    direction: 'Cảm nhận mình thuộc về gia đình đời đời của Đức Chúa Trời.',
    scripture: 'Dường ấy anh em chẳng phải là người ngoại, cũng chẳng phải là kẻ ở trọ nữa, nhưng là người đồng dân với các thánh đồ, và là người nhà của Đức Chúa Trời.',
    ref: 'Ê-phê-sô 2:19',
    comfort: 'Bạn không phải kẻ ở trọ hay người ngoài; bạn là thành viên quý báu trong nhà Chúa.',
    x: 0.16, y: 0.44
  },
  {
    id: 'need_seen',
    label: 'Được Nhìn Nhận',
    icon: '👁️',
    wound: 'Bị phớt lờ, tiếng nói không được coi trọng trong quá khứ.',
    belief: '“Mình không quan trọng, không ai nhìn thấy mình.”',
    pattern: 'Cố gây chú ý quá mức hoặc hoàn toàn thu mình, bất cần.',
    direction: 'Đức Chúa Trời biết và nhìn thấy tỏ tường con người thật cùng giá trị của mình.',
    scripture: 'Hỡi Đức Giê-hô-va, Ngài đã dò xét tôi, và biết tôi. Khi tôi ngồi, lúc tôi đứng dậy, Chúa đều biết cả; từ xa Chúa hiểu biết ý tưởng tôi.',
    ref: 'Thi-thiên 139:1-2',
    comfort: 'Chúa thấu suốt mọi hành trình và nhìn thấy từng nỗi lòng thầm kín nhất của bạn.',
    x: 0.84, y: 0.44
  },
  {
    id: 'need_valued',
    label: 'Có Giá Trị',
    icon: '💎',
    wound: 'Thường xuyên bị so sánh, tình yêu gắn với thành tích hoặc từng trải qua thất bại.',
    belief: '“Giá trị của mình = thành tích và sự thành công.”',
    pattern: 'Chủ nghĩa hoàn hảo hóa, ganh đua so sánh, hoặc tự khinh chê bản thân.',
    direction: 'Giá trị của con người có trước mọi thành tích, do chính Chúa tạo dựng.',
    scripture: 'Vì chúng ta là việc Ngài làm ra, đã được dựng nên trong Đức Chúa Jêsus Christ để làm việc lành mà Đức Chúa Trời đã sắm sẵn trước cho chúng ta làm theo.',
    ref: 'Ê-phê-sô 2:10',
    comfort: 'Bạn là kiệt tác nghệ thuật tuyệt hảo được chính bàn tay Chúa tạo tác.',
    x: 0.26, y: 0.25
  },
  {
    id: 'need_safe',
    label: 'Được An Toàn',
    icon: '🛡️',
    wound: 'Trải qua bạo lực, môi trường bất ổn hoặc người chăm sóc có phản ứng khó đoán.',
    belief: '“Thế giới này không an toàn, nguy hiểm rình rập.”',
    pattern: 'Luôn cảnh giác cao độ, muốn kiểm soát mọi thứ xung quanh hoặc né tránh.',
    direction: 'Sự khác biệt giữa kiểm soát và nương náu dưới bóng toàn năng của Chúa.',
    scripture: 'Hỡi Đức Giê-hô-va, tôi sẽ nằm và ngủ bình an; vì chỉ một mình Ngài làm cho tôi được ở yên ổn.',
    ref: 'Thi-thiên 4:8',
    comfort: 'Chỉ duy nơi Chúa mới có sự yên ổn và bình an đích thực bảo bọc giấc ngủ bạn.',
    x: 0.74, y: 0.25
  },
  {
    id: 'need_protected',
    label: 'Có Người Bảo Vệ',
    icon: '🏰',
    wound: 'Phải trưởng thành quá sớm, thiếu người nâng đỡ, gánh vác một mình.',
    belief: '“Mình phải tự lo tất cả, không ai bảo vệ che chở cho mình.”',
    pattern: 'Không dám nhờ giúp đỡ, luôn cố gồng gánh tự giải quyết mọi chuyện.',
    direction: 'Cho phép bản thân yếu đuối và tìm nơi nương náu vững bền nơi Chúa.',
    scripture: 'Đức Chúa Trời là nơi nương náu và sức lực của chúng tôi, Ngài sẵn giúp đỡ trong cơn gian truân.',
    ref: 'Thi-thiên 46:1',
    comfort: 'Bạn được phép yếu đuối trước mặt Chúa, vì Ngài chính là đồn lũy bảo vệ bạn.',
    x: 0.50, y: 0.15
  },
  {
    id: 'need_listened',
    label: 'Được Lắng Nghe',
    icon: '👂',
    wound: 'Cảm xúc thường bị phủ nhận như “đừng khóc”, “có gì đâu mà buồn”, “chuyện nhỏ”.',
    belief: '“Cảm xúc của mình không quan trọng, nói ra cũng vô ích.”',
    pattern: 'Đè nén, kìm nén, né tránh hoặc không biết cách gọi tên cảm xúc thật.',
    direction: 'Đức Chúa Trời trân quý và mời gọi con người giãi bày, dốc đổ lòng mình.',
    scripture: 'Mắt Đức Giê-hô-va đoái xem người công bình, lỗ tai Ngài lắng nghe tiếng kêu cầu của họ... Đức Giê-hô-va ở gần những người có lòng đau thương.',
    ref: 'Thi-thiên 34:15, 18',
    comfort: 'Tai Chúa hằng lắng nghe từng tiếng thở dài và giọt nước mắt thầm kín của bạn.',
    x: 0.38, y: 0.20
  },
  {
    id: 'need_understood',
    label: 'Được Thấu Hiểu',
    icon: '🕊️',
    wound: 'Thường xuyên bị hiểu lầm, phán xét hoặc áp đặt định kiến.',
    belief: '“Không ai thực sự hiểu mình, chia sẻ ra chỉ thêm tổn thương.”',
    pattern: 'Không chia sẻ, khép kín, giữ kín mọi suy nghĩ và nỗi đau trong lòng.',
    direction: 'Một Đấng thấu hiểu tận cùng lòng dạ, cả những điều chưa thể thốt nên lời.',
    scripture: 'Vì lời chưa ở trên lưỡi tôi, kìa, hỡi Đức Giê-hô-va, Ngài đã biết trọn hết rồi.',
    ref: 'Thi-thiên 139:4',
    comfort: 'Ngay cả khi bạn chưa biết cất lời ra sao, Chúa đã thấu hiểu tường tận cõi lòng bạn.',
    x: 0.62, y: 0.20
  },
  {
    id: 'need_rest',
    label: 'Được Nghỉ Ngơi',
    icon: '🌿',
    wound: 'Quá tải, gánh trách nhiệm quá mức hoặc luôn phải tỏ ra mạnh mẽ.',
    belief: '“Nếu mình dừng lại, mọi thứ sẽ sụp đổ; mình không được phép nghỉ.”',
    pattern: 'Làm việc quá mức, khó thả lỏng, ám ảnh năng suất, dễ kiệt sức.',
    direction: 'Giá trị bản thân không phụ thuộc vào năng suất; sự yên nghỉ thánh thiện trong Chúa.',
    scripture: 'Hỡi những kẻ mệt mỏi và gánh nặng, hãy đến cùng ta, ta sẽ cho các ngươi được yên nghỉ.',
    ref: 'Ma-thi-ơ 11:28',
    comfort: 'Hãy hạ bớt những gánh nặng tự mang; Chúa trao cho bạn sự yên nghỉ thanh thản.',
    x: 0.44, y: 0.36
  },
  {
    id: 'need_forgiven',
    label: 'Được Tha Thứ',
    icon: '🕊️',
    wound: 'Từng mắc sai lầm, phạm tội hoặc mang cảm giác xấu hổ, tội lỗi sâu sắc.',
    belief: '“Mình là người xấu, không thể tha thứ, không xứng đáng được hạnh phúc.”',
    pattern: 'Trốn tránh, che giấu, dằn vặt tự trách hoặc tự trừng phạt bản thân.',
    direction: 'Sự khác biệt giữa tội lỗi và sự kết án; huyết Chúa tha thứ và tẩy sạch hoàn toàn.',
    scripture: 'Còn nếu chúng ta xưng tội mình, thì Ngài là thành tín công bình để tha tội cho chúng ta, và làm cho chúng ta sạch mọi điều gian ác.',
    ref: 'I Giăng 1:9',
    comfort: 'Ân điển tha thứ của Chúa xóa sạch mọi vết nhơ và cho bạn một lương tâm trong sạch.',
    x: 0.56, y: 0.36
  },
  {
    id: 'need_restored',
    label: 'Được Phục Hồi',
    icon: '🌱',
    wound: 'Thất bại nặng nề, sa ngã hoặc mất phương hướng hoàn toàn.',
    belief: '“Mình đã hỏng rồi, không thể làm lại, cuộc đời coi như xong.”',
    pattern: 'Buông xuôi, mất hy vọng, chán chường, không muốn bắt đầu lại.',
    direction: 'Thất bại không phải là điểm kết thúc; Chúa là Đấng tái tạo và bù đắp bội phần.',
    scripture: 'Đức Chúa Trời ôi! xin hãy dựng nên trong tôi một lòng trong sạch, và làm cho mới lại trong tôi một thần linh ngay thẳng.',
    ref: 'Thi-thiên 51:10',
    comfort: 'Chúa có quyền năng tái tạo một tâm linh hoàn toàn mới và tươi sáng trong bạn.',
    x: 0.50, y: 0.37
  },
  {
    id: 'need_free',
    label: 'Được Tự Do',
    icon: '🕊️',
    wound: 'Bị kiểm soát ngột ngạt, chịu áp lực định kiến hoặc phải sống theo mong muốn của người khác.',
    belief: '“Mình không được phép là chính mình, luôn bị trói buộc.”',
    pattern: 'Nổi loạn cực đoan hoặc ngược lại phục tùng tuyệt đối trong cay đắng.',
    direction: 'Sự tự do thật trong Chúa (không đồng nghĩa với phóng túng hay vô trách nhiệm).',
    scripture: 'Chúa tức là Thánh Linh, nơi nào có Thánh Linh của Chúa, nơi đó có sự tự do.',
    ref: 'II Cô-rinh-tô 3:17',
    comfort: 'Thánh Linh Chúa mang lại cho bạn sự tự do và sự sống đích thực trong tâm hồn.',
    x: 0.50, y: 0.46
  }
];

// 2. AUDIO SYNTHESIZER (528Hz & Water Sounds)
class SanctuaryAudio {
  constructor() {
    this.ctx = null;
    this.bgMusic = new Audio('/nhac-cau-nguyen.mp3');
    this.bgMusic.loop = true; // Hết bài tự phát lại từ đầu
    this.bgMusic.preload = 'auto';
    this.bgMusic.volume = 0.55;
    this.hasStartedMusic = false;
  }

  init() {
    if (!this.ctx) {
      const AudioCtx = window.AudioContext || window.webkitAudioContext;
      this.ctx = new AudioCtx();
    }
    if (this.ctx && this.ctx.state === 'suspended') {
      this.ctx.resume();
    }
  }

  startBgMusic() {
    if (store && !store.isSoundEnabled()) return;
    this.bgMusic.play().then(() => {
      this.hasStartedMusic = true;
      this.updateMusicButtonUI(true);
    }).catch((err) => {
      console.log('Autoplay chờ tương tác đầu tiên của người dùng:', err);
    });
  }

  stopBgMusic() {
    this.bgMusic.pause();
    this.updateMusicButtonUI(false);
  }

  toggleBgMusic() {
    if (this.bgMusic.paused) {
      store.setSoundEnabled(true);
      this.startBgMusic();
      showBanner('Đã bật Nhạc Nền Cầu Nguyện 🎵');
    } else {
      store.setSoundEnabled(false);
      this.stopBgMusic();
      showBanner('Đã tạm dừng Nhạc Nền 🔇');
    }
  }

  updateMusicButtonUI(isPlaying) {
    const btn = document.getElementById('btn-toggle-music');
    if (btn) {
      btn.innerText = isPlaying ? '🎵' : '🔇';
      btn.title = isPlaying ? 'Đang phát Nhạc Cầu Nguyện (Bấm để tắt)' : 'Nhạc đang tắt (Bấm để bật)';
    }
    const soundToggle = document.getElementById('settings-sound-toggle');
    if (soundToggle) {
      soundToggle.checked = isPlaying;
    }
  }

  playBell(freq = 528) {
    if (store && !store.isSoundEnabled()) return;
    try {
      this.init();
      if (!this.ctx) return;
      const now = this.ctx.currentTime;
      const osc = this.ctx.createOscillator();
      const gain = this.ctx.createGain();

      osc.type = 'sine';
      osc.frequency.setValueAtTime(freq, now);

      gain.gain.setValueAtTime(0.001, now);
      gain.gain.exponentialRampToValueAtTime(0.35, now + 0.05);
      gain.gain.exponentialRampToValueAtTime(0.0001, now + 3.0);

      osc.connect(gain);
      gain.connect(this.ctx.destination);

      osc.start(now);
      osc.stop(now + 3.1);
    } catch (e) {
      console.warn('Audio play failed', e);
    }
  }

  playWaterDrop() {
    if (store && !store.isSoundEnabled()) return;
    try {
      this.init();
      if (!this.ctx) return;
      const now = this.ctx.currentTime;
      const osc = this.ctx.createOscillator();
      const gain = this.ctx.createGain();

      osc.type = 'sine';
      osc.frequency.setValueAtTime(900, now);
      osc.frequency.exponentialRampToValueAtTime(350, now + 0.15);

      gain.gain.setValueAtTime(0.2, now);
      gain.gain.exponentialRampToValueAtTime(0.001, now + 0.2);

      osc.connect(gain);
      gain.connect(this.ctx.destination);

      osc.start(now);
      osc.stop(now + 0.25);
    } catch (e) {
      console.warn('Audio drop failed', e);
    }
  }
}

const audio = new SanctuaryAudio();

// 3. STORAGE & AUTH
class LocalStore {
  constructor() {
    this.prefix = 'css_';
    this.initDefaultUsers();
  }

  isSoundEnabled() {
    return localStorage.getItem(this.prefix + 'sound_enabled') !== 'false';
  }

  setSoundEnabled(val) {
    localStorage.setItem(this.prefix + 'sound_enabled', val ? 'true' : 'false');
  }

  initDefaultUsers() {
    if (!localStorage.getItem(this.prefix + 'accounts')) {
      const accs = [
        { username: 'admin', password: '123', isAdmin: true, displayName: 'Quản Trị Viên' }
      ];
      for (let i = 1; i <= 20; i++) {
        accs.push({
          username: `nguoidung${i}`,
          password: '12345678',
          isAdmin: false,
          displayName: `Thành Viên ${i}`
        });
      }
      localStorage.setItem(this.prefix + 'accounts', JSON.stringify(accs));
    }
  }

  getAccounts() {
    try {
      return JSON.parse(localStorage.getItem(this.prefix + 'accounts')) || [];
    } catch {
      return [];
    }
  }

  addAccount(uname) {
    const list = this.getAccounts();
    const clean = uname.trim().toLowerCase();
    if (list.some(a => a.username.toLowerCase() === clean)) return false;
    list.push({
      username: clean,
      password: '12345678',
      isAdmin: false,
      displayName: clean
    });
    localStorage.setItem(this.prefix + 'accounts', JSON.stringify(list));
    return true;
  }

  registerAccount(uname, displayName, password) {
    const list = this.getAccounts();
    const clean = uname.trim().toLowerCase();
    if (!clean || clean.length < 3) {
      return { success: false, msg: 'Tên đăng nhập cần ít nhất 3 ký tự.' };
    }
    if (!/^[a-zA-Z0-9_]+$/.test(clean)) {
      return { success: false, msg: 'Tên đăng nhập chỉ chứa chữ cái, số và dấu gạch dưới (_).' };
    }
    if (list.some(a => a.username.toLowerCase() === clean)) {
      return { success: false, msg: 'Tên đăng nhập này đã được sử dụng!' };
    }
    if (!password || password.length < 4) {
      return { success: false, msg: 'Mật khẩu phải có ít nhất 4 ký tự.' };
    }

    const newAcc = {
      username: clean,
      password: password,
      isAdmin: false,
      displayName: displayName.trim() || clean
    };
    list.push(newAcc);
    localStorage.setItem(this.prefix + 'accounts', JSON.stringify(list));
    return { success: true, account: newAcc };
  }

  updateProfile(uname, newDisplayName, currentPwd, newPwd) {
    const list = this.getAccounts();
    const idx = list.findIndex(a => a.username.toLowerCase() === uname.toLowerCase());
    if (idx === -1) return { success: false, msg: 'Không tìm thấy tài khoản!' };

    const acc = list[idx];

    // If changing password, verify current password
    if (newPwd) {
      if (!currentPwd) {
        return { success: false, msg: 'Vui lòng nhập Mật khẩu hiện tại để xác thực đổi mật khẩu!' };
      }
      if (acc.password !== currentPwd) {
        return { success: false, msg: 'Mật khẩu hiện tại không chính xác!' };
      }
      if (newPwd.length < 4) {
        return { success: false, msg: 'Mật khẩu mới phải có ít nhất 4 ký tự!' };
      }
      acc.password = newPwd;
    }

    if (newDisplayName && newDisplayName.trim()) {
      acc.displayName = newDisplayName.trim();
    }

    list[idx] = acc;
    localStorage.setItem(this.prefix + 'accounts', JSON.stringify(list));
    return { success: true, account: acc };
  }

  resetMemberPassword(uname) {
    const list = this.getAccounts();
    const idx = list.findIndex(a => a.username.toLowerCase() === uname.toLowerCase());
    if (idx === -1) return false;
    list[idx].password = '12345678';
    localStorage.setItem(this.prefix + 'accounts', JSON.stringify(list));
    return true;
  }

  deleteMember(uname) {
    let list = this.getAccounts();
    list = list.filter(a => a.username.toLowerCase() !== uname.toLowerCase());
    localStorage.setItem(this.prefix + 'accounts', JSON.stringify(list));
    return true;
  }

  getUserData(uname) {
    try {
      const data = localStorage.getItem(this.prefix + 'user_' + uname);
      if (data) return JSON.parse(data);
    } catch {}
    return {
      waterDrops: 0,
      amenFruits: [],
      amenLeaves: [],
      isNegative: false,
      journals: [],
      chats: [
        { role: 'bot', text: '🕊️ Bình an cho con! Ta là Đấng Lắng Nghe Nhân Từ. Con đang có tâm sự hay gánh nặng gì muốn dốc đổ cùng Cha hôm nay?' }
      ]
    };
  }

  saveUserData(uname, data) {
    localStorage.setItem(this.prefix + 'user_' + uname, JSON.stringify(data));
  }

  getGeminiKey() {
    return localStorage.getItem(this.prefix + 'gemini_key') || '';
  }

  saveGeminiKey(key) {
    localStorage.setItem(this.prefix + 'gemini_key', key.trim());
  }
}

const store = new LocalStore();
let currentUser = null;
let currentTab = 'tab-tree';

// ANIMATION STATES
let isWateringAnim = false;
let wateringProgress = 0;
let isHolyFireAnim = false;
let fireProgress = 0;
let rockDissolve = 0; // 0 = rocks solid, 1 = rocks completely vanished

// CANVAS VISUALIZER
const canvas = document.getElementById('tree-canvas');
const ctx = canvas.getContext('2d');
let animFrameId = null;

function resizeCanvas() {
  const rect = canvas.getBoundingClientRect();
  canvas.width = rect.width * window.devicePixelRatio;
  canvas.height = rect.height * window.devicePixelRatio;
  ctx.scale(window.devicePixelRatio, window.devicePixelRatio);
}

window.addEventListener('resize', resizeCanvas);

// CANVAS DRAWING LOOP
let tick = 0;
function drawTree() {
  tick += 0.03;
  const w = canvas.getBoundingClientRect().width;
  const h = canvas.getBoundingClientRect().height;
  ctx.clearRect(0, 0, w, h);

  const soilY = h * 0.72;
  const uData = currentUser ? store.getUserData(currentUser.username) : { waterDrops: 0, isNegative: false };

  // 1. SKY GRADIENT
  const skyGrad = ctx.createLinearGradient(0, 0, 0, soilY);
  skyGrad.addColorStop(0, '#06030F');
  skyGrad.addColorStop(1, '#1A0E38');
  ctx.fillStyle = skyGrad;
  ctx.fillRect(0, 0, w, soilY);

  // 2. STARS
  ctx.fillStyle = '#FFF';
  for (let i = 0; i < 28; i++) {
    const sx = (Math.sin(i * 99 + 1) * 0.5 + 0.5) * w;
    const sy = (Math.cos(i * 33 + 2) * 0.5 + 0.5) * (soilY * 0.7);
    const alpha = (Math.sin(tick * 2 + i) * 0.4 + 0.6);
    ctx.globalAlpha = alpha;
    ctx.fillRect(sx, sy, 2, 2);
  }
  ctx.globalAlpha = 1.0;

  // 3. SOIL
  const soilGrad = ctx.createLinearGradient(0, soilY, 0, h);
  soilGrad.addColorStop(0, '#381E11');
  soilGrad.addColorStop(0.5, '#24140B');
  soilGrad.addColorStop(1, '#130B06');
  ctx.fillStyle = soilGrad;
  ctx.fillRect(0, soilY, w, h - soilY);

  // 4. WATER VEINS UNDERGROUND (When watering)
  if (isWateringAnim) {
    ctx.strokeStyle = '#06B6D4';
    ctx.lineWidth = 3;
    ctx.globalAlpha = Math.min(1.0, wateringProgress * 1.5);
    ctx.beginPath();
    ctx.moveTo(w * 0.5, soilY);
    ctx.quadraticCurveTo(w * 0.4, soilY + 50, w * 0.35, h * 0.95);
    ctx.moveTo(w * 0.5, soilY);
    ctx.quadraticCurveTo(w * 0.6, soilY + 50, w * 0.65, h * 0.95);
    ctx.stroke();
    ctx.globalAlpha = 1.0;
  }

  // DYNAMIC GROWTH LEVEL (Từ mầm non mảnh mai 3 rễ -> Đại thụ rễ sâu to lớn)
  const totalScore = (uData.waterDrops || 0) + 
                     (uData.amenFruits?.length || 0) * 12 + 
                     (uData.amenLeaves?.length || 0) * 10 + 
                     (uData.journals?.length || 0) * 15;
  // growth: 0.18 (cây non ban đầu bé) -> 1.0 (đại thụ to lớn)
  const growth = Math.min(1.0, Math.max(0.18, totalScore / 180));

  // 5. DYNAMIC ROOTS SYSTEM (Mọc thêm rễ & rễ to lên theo thời gian)
  const rootColor = '#8D532B';
  const rootGlowColor = isWateringAnim ? '#FEF08A' : 'rgba(56, 189, 248, 0.4)';

  // Danh sách các nhánh rễ mở rộng theo độ tăng trưởng (growth)
  const dynamicRoots = [
    // 3 Nhánh rễ chính ban đầu (luôn có)
    { start: [w * 0.49, soilY], end: [w * (0.5 - 0.26 * growth), soilY + (h - soilY) * (0.65 + 0.30 * growth)], bend: w * (0.5 - 0.14 * growth), thick: 2.2 + 5.5 * growth },
    { start: [w * 0.50, soilY], end: [w * 0.49, soilY + (h - soilY) * (0.70 + 0.26 * growth)], bend: w * 0.50, thick: 2.8 + 6.2 * growth },
    { start: [w * 0.51, soilY], end: [w * (0.5 + 0.26 * growth), soilY + (h - soilY) * (0.65 + 0.30 * growth)], bend: w * (0.5 + 0.14 * growth), thick: 2.2 + 5.5 * growth }
  ];

  // Mọc thêm 2 rễ phụ khi cây bắt đầu lớn (growth >= 0.35)
  if (growth >= 0.35) {
    dynamicRoots.push(
      { start: [w * 0.48, soilY + 10], end: [w * (0.5 - 0.38 * growth), soilY + (h - soilY) * 0.60], bend: w * (0.5 - 0.24 * growth), thick: 1.8 + 3.8 * growth },
      { start: [w * 0.52, soilY + 10], end: [w * (0.5 + 0.38 * growth), soilY + (h - soilY) * 0.60], bend: w * (0.5 + 0.24 * growth), thick: 1.8 + 3.8 * growth }
    );
  }

  // Mọc thêm 2 rễ sâu đâm vào dòng nước hằng sống (growth >= 0.65)
  if (growth >= 0.65) {
    dynamicRoots.push(
      { start: [w * 0.49, soilY + 25], end: [w * (0.5 - 0.16 * growth), soilY + (h - soilY) * 0.94], bend: w * (0.5 - 0.08 * growth), thick: 1.5 + 3.5 * growth },
      { start: [w * 0.51, soilY + 25], end: [w * (0.5 + 0.16 * growth), soilY + (h - soilY) * 0.94], bend: w * (0.5 + 0.08 * growth), thick: 1.5 + 3.5 * growth }
    );
  }

  // Mọc thêm 2 rễ đại thụ xum xuê đan xen (growth >= 0.85)
  if (growth >= 0.85) {
    dynamicRoots.push(
      { start: [w * 0.47, soilY + 18], end: [w * 0.18, soilY + (h - soilY) * 0.82], bend: w * 0.30, thick: 2.0 + 2.5 * growth },
      { start: [w * 0.53, soilY + 18], end: [w * 0.82, soilY + (h - soilY) * 0.82], bend: w * 0.70, thick: 2.0 + 2.5 * growth }
    );
  }

  // Cập nhật nhãn thông tin cấp độ tăng trưởng trên UI
  const stageEl = document.getElementById('tree-stage-name');
  if (stageEl) {
    if (growth < 0.35) {
      stageEl.innerHTML = '🌱 Mầm Non Tĩnh Nguyện (Cấp 1)';
    } else if (growth < 0.65) {
      stageEl.innerHTML = '🌿 Cây Đang Tăng Trưởng (Cấp 2)';
    } else if (growth < 0.85) {
      stageEl.innerHTML = '🌳 Cây Trưởng Thành Bền Vững (Cấp 3)';
    } else {
      stageEl.innerHTML = '✨ Đại Thụ Sự Sống Vinh Hiển (Cấp 4)';
    }
  }

  // Vẽ các nhánh rễ
  dynamicRoots.forEach((r) => {
    ctx.beginPath();
    ctx.strokeStyle = rootColor;
    ctx.lineWidth = r.thick;
    ctx.lineCap = 'round';
    ctx.moveTo(r.start[0], r.start[1]);
    ctx.quadraticCurveTo(r.bend, (soilY + r.end[1]) / 2, r.end[0], r.end[1]);
    ctx.stroke();

    // Rễ tơ phụ nhỏ tỏa ra
    if (growth >= 0.45) {
      ctx.beginPath();
      ctx.strokeStyle = '#6E3A1A';
      ctx.lineWidth = Math.max(1, r.thick * 0.35);
      const midX = (r.start[0] + r.end[0]) / 2;
      const midY = (soilY + r.end[1]) / 2;
      ctx.moveTo(midX, midY);
      ctx.lineTo(midX + (r.end[0] > w * 0.5 ? 12 : -12), midY + 14);
      ctx.stroke();
    }

    // Hiệu ứng ánh sáng dòng nước hằng sống trong rễ cây
    if (isWateringAnim || growth >= 0.7) {
      ctx.beginPath();
      ctx.strokeStyle = isWateringAnim ? '#FEF08A' : 'rgba(56, 189, 248, 0.25)';
      ctx.lineWidth = Math.max(1.2, r.thick * 0.4);
      ctx.globalAlpha = isWateringAnim ? Math.min(1.0, wateringProgress * 1.5) : (0.3 + Math.sin(tick * 2) * 0.2);
      ctx.moveTo(r.start[0], r.start[1]);
      ctx.quadraticCurveTo(r.bend, (soilY + r.end[1]) / 2, r.end[0], r.end[1]);
      ctx.stroke();
      ctx.globalAlpha = 1.0;
    }
  });

  // 6. ROCKS (Dissolve during watering)
  const rockAlpha = uData.isNegative ? Math.max(0, 1.0 - rockDissolve) : 0;
  if (rockAlpha > 0.02) {
    ctx.globalAlpha = rockAlpha;
    const rockPositions = [
      { x: w * 0.30, y: soilY + 40, r: 18 },
      { x: w * 0.68, y: soilY + 45, r: 20 },
      { x: w * 0.48, y: soilY + 70, r: 15 }
    ];

    rockPositions.forEach(rock => {
      ctx.fillStyle = '#64748B';
      ctx.beginPath();
      ctx.arc(rock.x, rock.y, rock.r, 0, Math.PI * 2);
      ctx.fill();

      ctx.fillStyle = '#94A3B8';
      ctx.beginPath();
      ctx.arc(rock.x - rock.r * 0.3, rock.y - rock.r * 0.3, rock.r * 0.4, 0, Math.PI * 2);
      ctx.fill();

      if (isWateringAnim && wateringProgress > 0.2) {
        ctx.strokeStyle = '#67E8F9';
        ctx.lineWidth = 2;
        ctx.beginPath();
        ctx.moveTo(rock.x - rock.r * 0.6, rock.y);
        ctx.lineTo(rock.x + rock.r * 0.5, rock.y + 4);
        ctx.stroke();
      }
    });
    ctx.globalAlpha = 1.0;
  }

  // 7. GRASS CRUST
  const grassColor = isWateringAnim ? '#16A34A' : (uData.isNegative ? '#A16207' : '#16A34A');
  ctx.fillStyle = grassColor;
  ctx.beginPath();
  ctx.moveTo(0, soilY);
  ctx.quadraticCurveTo(w * 0.5, soilY - 14, w, soilY);
  ctx.lineTo(w, soilY + 8);
  ctx.lineTo(0, soilY + 8);
  ctx.fill();

  // 8. DYNAMIC TREE TRUNK (Ban đầu bé thanh mảnh, to dần thành đại thụ vững chãi)
  const trunkBaseSpread = w * (0.025 + 0.055 * growth); // Bề rộng gốc: nhỏ khi mầm non, to khi trưởng thành
  const trunkMidSpread = w * (0.015 + 0.035 * growth);  // Bề rộng thân giữa
  const branchSpread = w * (0.08 + 0.12 * growth);      // Tán cành tỏa rộng

  const trunkGrad = ctx.createLinearGradient(w * (0.5 - trunkBaseSpread), 0, w * (0.5 + trunkBaseSpread), 0);
  trunkGrad.addColorStop(0, '#452715');
  trunkGrad.addColorStop(0.5, '#784423');
  trunkGrad.addColorStop(1, '#452715');

  ctx.fillStyle = trunkGrad;
  ctx.beginPath();
  // Gốc cây bên trái
  ctx.moveTo(w * 0.5 - trunkBaseSpread, soilY);
  // Thân dưới -> thân giữa -> cành trái
  ctx.bezierCurveTo(w * 0.5 - trunkMidSpread, h * 0.60, w * 0.5 - trunkMidSpread * 0.8, h * 0.40, w * 0.5 - branchSpread, h * 0.28);
  // Nhánh chẽ giữa
  ctx.bezierCurveTo(w * 0.5 - trunkMidSpread * 0.5, h * 0.35, w * 0.5, h * 0.42, w * 0.5, h * 0.48);
  // Cành phải
  ctx.bezierCurveTo(w * 0.5 + trunkMidSpread * 0.5, h * 0.35, w * 0.5 + branchSpread * 0.8, h * 0.30, w * 0.5 + branchSpread, h * 0.28);
  // Thân giữa -> Gốc cây bên phải
  ctx.bezierCurveTo(w * 0.5 + trunkMidSpread * 0.8, h * 0.40, w * 0.5 + trunkMidSpread, h * 0.60, w * 0.5 + trunkBaseSpread, soilY);
  ctx.closePath();
  ctx.fill();

  // 9. BRAMBLES & TARES (Burn during Holy Fire)
  const weedAlpha = uData.isNegative ? (isHolyFireAnim ? Math.max(0, 1.0 - fireProgress) : 1.0) : 0;
  if (weedAlpha > 0.02) {
    ctx.globalAlpha = weedAlpha;
    ctx.strokeStyle = '#991B1B';
    ctx.lineWidth = 3.5;
    ctx.beginPath();
    ctx.arc(w * 0.5, soilY - 10, 32, Math.PI, 0);
    ctx.stroke();

    const tares = [
      { x: w * 0.28, h: 65, lean: -8 },
      { x: w * 0.34, h: 78, lean: 4 },
      { x: w * 0.66, h: 72, lean: -4 },
      { x: w * 0.72, h: 84, lean: 8 }
    ];

    tares.forEach(t => {
      const isBurning = isHolyFireAnim && fireProgress > 0.2;
      ctx.strokeStyle = isBurning ? '#F97316' : '#78350F';
      ctx.lineWidth = isBurning ? 4 : 2.5;
      ctx.beginPath();
      ctx.moveTo(t.x, soilY);
      ctx.quadraticCurveTo(t.x + t.lean * 0.5, soilY - t.h * 0.6, t.x + t.lean, soilY - t.h);
      ctx.stroke();

      ctx.fillStyle = isBurning ? '#FEF08A' : '#451A03';
      ctx.beginPath();
      ctx.ellipse(t.x + t.lean, soilY - t.h - 5, 5, 10, 0, 0, Math.PI * 2);
      ctx.fill();

      if (isBurning) {
        ctx.fillStyle = '#EF4444';
        ctx.beginPath();
        ctx.arc(t.x + t.lean, soilY - t.h - 10 + Math.sin(tick * 5) * 4, 8, 0, Math.PI * 2);
        ctx.fill();
      }
    });
    ctx.globalAlpha = 1.0;
  }

  // 10. CANOPY FOLIAGE CLUSTERS
  const canopies = [
    { x: w * 0.50, y: h * 0.18, r: w * 0.18 },
    { x: w * 0.33, y: h * 0.25, r: w * 0.16 },
    { x: w * 0.67, y: h * 0.25, r: w * 0.16 },
    { x: w * 0.22, y: h * 0.36, r: w * 0.14 },
    { x: w * 0.78, y: h * 0.36, r: w * 0.14 },
    { x: w * 0.38, y: h * 0.40, r: w * 0.13 },
    { x: w * 0.62, y: h * 0.40, r: w * 0.13 }
  ];

  canopies.forEach((c) => {
    const folGrad = ctx.createRadialGradient(c.x, c.y, c.r * 0.2, c.x, c.y, c.r);
    folGrad.addColorStop(0, '#10B981');
    folGrad.addColorStop(1, '#065F46');
    ctx.fillStyle = folGrad;
    ctx.beginPath();
    ctx.arc(c.x, c.y, c.r, 0, Math.PI * 2);
    ctx.fill();
  });

  // 11. FRUITS OF THE SPIRIT (9 Fruits)
  SPIRIT_FRUITS.forEach(f => {
    const fx = w * f.x;
    const fy = h * f.y;
    const isAmen = uData.amenFruits && uData.amenFruits.includes(f.id);

    // Glowing aura
    if (isAmen) {
      const auraPulse = (Math.sin(tick * 3 + f.x * 10) * 0.25 + 0.45);
      ctx.fillStyle = `rgba(254, 240, 138, ${auraPulse})`;
      ctx.beginPath();
      ctx.arc(fx, fy, 18, 0, Math.PI * 2);
      ctx.fill();
    } else {
      ctx.fillStyle = 'rgba(255, 255, 255, 0.12)';
      ctx.beginPath();
      ctx.arc(fx, fy, 15, 0, Math.PI * 2);
      ctx.fill();
    }

    ctx.fillStyle = isAmen ? '#F59E0B' : f.color;
    ctx.beginPath();
    ctx.arc(fx, fy, 11, 0, Math.PI * 2);
    ctx.fill();

    ctx.fillStyle = '#FFF';
    ctx.beginPath();
    ctx.arc(fx - 3, fy - 3, 3, 0, Math.PI * 2);
    ctx.fill();
  });

  // 12. LEAVES (13 Leaves)
  CORE_NEEDS.forEach(l => {
    const lx = w * l.x;
    const ly = h * l.y;
    const isAmen = uData.amenLeaves && uData.amenLeaves.includes(l.id);

    // Subtle glow if Amen-ed or active
    if (isAmen) {
      const leafGlow = (Math.sin(tick * 2.5 + l.x * 8) * 0.25 + 0.4);
      ctx.fillStyle = `rgba(110, 231, 183, ${leafGlow})`;
      ctx.beginPath();
      ctx.arc(lx, ly, 10, 0, Math.PI * 2);
      ctx.fill();
    }

    ctx.fillStyle = isAmen ? '#6EE7B7' : '#059669';
    ctx.beginPath();
    ctx.ellipse(lx, ly, 4.5, 9, Math.sin(tick + l.x * 10) * 0.3, 0, Math.PI * 2);
    ctx.fill();
  });

  // 13. CINEMATIC ANGEL WATERING ANIMATION
  // (Thiên sứ cầm bình vàng -> Đổ nước -> Tụ thành mây -> Mưa rơi 5-10s -> Cây tươi tốt -> Chim bồ câu bay)
  if (isWateringAnim) {
    const p = wateringProgress; // 0.0 -> 1.0

    // --- PHASE 1 & 2: THIÊN SỨ CẦM BÌNH VÀNG & ĐỔ NƯỚC (p: 0.0 -> 0.38) ---
    if (p < 0.45) {
      const angelAlpha = p < 0.35 ? 1.0 : Math.max(0, 1.0 - (p - 0.35) * 10);
      ctx.globalAlpha = angelAlpha;

      const angelX = w * 0.50 + Math.sin(tick * 2) * 5;
      const angelY = h * 0.10 + Math.sin(tick * 1.5) * 4;

      // Hào quang thiên sứ
      const angelGlow = ctx.createRadialGradient(angelX, angelY, 10, angelX, angelY, 60);
      angelGlow.addColorStop(0, 'rgba(254, 240, 138, 0.6)');
      angelGlow.addColorStop(1, 'rgba(254, 240, 138, 0.0)');
      ctx.fillStyle = angelGlow;
      ctx.beginPath();
      ctx.arc(angelX, angelY, 60, 0, Math.PI * 2);
      ctx.fill();

      // Đôi cánh thiên thần trắng phát sáng vỗ nhịp
      const wingFlap = Math.sin(tick * 10) * 6;
      ctx.fillStyle = '#FFF';
      ctx.beginPath();
      ctx.ellipse(angelX - 26, angelY - 2 + wingFlap, 24, 12, -0.35, 0, Math.PI * 2);
      ctx.ellipse(angelX + 26, angelY - 2 + wingFlap, 24, 12, 0.35, 0, Math.PI * 2);
      ctx.fill();

      // Đầu & Thân thiên sứ
      ctx.fillStyle = '#FEF08A';
      ctx.beginPath();
      ctx.arc(angelX, angelY - 10, 9, 0, Math.PI * 2);
      ctx.fill();

      ctx.fillStyle = '#FFFFFF';
      ctx.beginPath();
      ctx.moveTo(angelX, angelY - 2);
      ctx.lineTo(angelX - 15, angelY + 28);
      ctx.lineTo(angelX + 15, angelY + 28);
      ctx.closePath();
      ctx.fill();

      // Bình nước vàng ròng (Golden Urn)
      const isPouring = p >= 0.15;
      const urnTilt = isPouring ? -0.85 : -0.2;
      const urnX = angelX + 12;
      const urnY = angelY + 14;

      ctx.save();
      ctx.translate(urnX, urnY);
      ctx.rotate(urnTilt);
      ctx.fillStyle = '#F59E0B';
      ctx.beginPath();
      ctx.ellipse(0, 0, 9, 14, 0, 0, Math.PI * 2);
      ctx.fill();
      // Quai bình & miệng bình vàng
      ctx.strokeStyle = '#FEF08A';
      ctx.lineWidth = 2.5;
      ctx.stroke();
      ctx.restore();

      // Dòng nước sự sống tuôn trào từ miệng bình xuống giữa trời
      if (isPouring) {
        ctx.fillStyle = '#67E8F9';
        ctx.beginPath();
        ctx.moveTo(urnX + 4, urnY + 6);
        ctx.quadraticCurveTo(w * 0.49, h * 0.18, w * 0.50, h * 0.20);
        ctx.lineTo(w * 0.52, h * 0.20);
        ctx.quadraticCurveTo(w * 0.51, h * 0.18, urnX + 8, urnY + 4);
        ctx.fill();

        // Hạt bụi vàng lấp lánh rơi
        for (let i = 0; i < 6; i++) {
          ctx.fillStyle = '#FEF08A';
          ctx.beginPath();
          ctx.arc(w * 0.50 + Math.sin(tick * 8 + i) * 12, h * 0.16 + i * 7, 2.5, 0, Math.PI * 2);
          ctx.fill();
        }
      }
      ctx.globalAlpha = 1.0;
    }

    // --- PHASE 3: ĐÁM MÂY THẦN THÁNH VÀ MƯA RÀO TƯƠI MÁT (p: 0.25 -> 0.88) ---
    if (p >= 0.25 && p <= 0.88) {
      const cloudAlpha = p < 0.35 ? (p - 0.25) * 10 : (p > 0.80 ? (0.88 - p) * 12 : 1.0);
      ctx.globalAlpha = Math.max(0, Math.min(1.0, cloudAlpha));

      const cloudY = h * 0.14 + Math.sin(tick * 1.5) * 3;
      const cloudX = w * 0.50;

      // Vẽ Đám Mây Thần Thánh phát sáng bồng bềnh
      const cloudGrad = ctx.createRadialGradient(cloudX, cloudY, 20, cloudX, cloudY, w * 0.30);
      cloudGrad.addColorStop(0, 'rgba(224, 242, 254, 0.95)');
      cloudGrad.addColorStop(0.5, 'rgba(56, 189, 248, 0.80)');
      cloudGrad.addColorStop(1, 'rgba(30, 58, 138, 0.0)');

      ctx.fillStyle = cloudGrad;
      ctx.beginPath();
      ctx.arc(cloudX, cloudY, 35, 0, Math.PI * 2);
      ctx.arc(cloudX - 45, cloudY + 4, 28, 0, Math.PI * 2);
      ctx.arc(cloudX + 45, cloudY + 4, 28, 0, Math.PI * 2);
      ctx.arc(cloudX - 80, cloudY + 10, 20, 0, Math.PI * 2);
      ctx.arc(cloudX + 80, cloudY + 10, 20, 0, Math.PI * 2);
      ctx.fill();

      // MƯA RÀO TƯƠI MÁT (Cơn mưa ánh sáng trút xuống cây và đất)
      ctx.strokeStyle = '#67E8F9';
      ctx.lineWidth = 2;
      ctx.lineCap = 'round';
      for (let i = 0; i < 40; i++) {
        const rx = w * 0.18 + ((i * 37 + Math.sin(i)) % (w * 0.64));
        const rainProgress = ((tick * 18 + i * 23) % 100) / 100;
        const ry = (cloudY + 20) + rainProgress * (soilY - cloudY - 15);
        const rLen = 14 + (i % 4) * 4;

        ctx.globalAlpha = Math.max(0, Math.min(0.9, cloudAlpha * 0.85));
        ctx.beginPath();
        ctx.moveTo(rx, ry);
        ctx.lineTo(rx - 1.5, ry + rLen);
        ctx.stroke();

        // Giọt nước chạm mặt đất tạo gợn sóng nước
        if (ry + rLen >= soilY - 5) {
          ctx.beginPath();
          ctx.strokeStyle = '#E0F2FE';
          ctx.lineWidth = 1.2;
          ctx.ellipse(rx, soilY + 2, 6, 2, 0, 0, Math.PI * 2);
          ctx.stroke();
        }
      }
      ctx.globalAlpha = 1.0;
    }

    // --- PHASE 4: CHIM BỒ CÂU TRẮNG 🕊️ BAY LƯỢN QUANH CÂY (p: 0.70 -> 1.0) ---
    if (p >= 0.70) {
      const doveAlpha = p < 0.80 ? (p - 0.70) * 10 : (p > 0.95 ? (1.0 - p) * 20 : 1.0);
      ctx.globalAlpha = Math.max(0, Math.min(1.0, doveAlpha));

      const doves = [
        { angle: tick * 2.2, radiusX: w * 0.26, radiusY: h * 0.12, cy: h * 0.28, phase: 0 },
        { angle: tick * 2.2 + Math.PI, radiusX: w * 0.32, radiusY: h * 0.15, cy: h * 0.32, phase: 0.5 }
      ];

      doves.forEach(d => {
        const dx = w * 0.50 + Math.cos(d.angle) * d.radiusX;
        const dy = d.cy + Math.sin(d.angle * 2) * (d.radiusY * 0.5);
        const flap = Math.sin(tick * 16 + d.phase) * 7;
        const heading = Math.cos(d.angle) > 0 ? 1 : -1;

        // Vẽ chim bồ câu trắng
        ctx.fillStyle = '#FFFFFF';
        ctx.beginPath();
        // Thân chim
        ctx.ellipse(dx, dy, 10, 5, heading * 0.2, 0, Math.PI * 2);
        ctx.fill();

        // Cánh chim đang vỗ
        ctx.beginPath();
        ctx.moveTo(dx - heading * 2, dy);
        ctx.lineTo(dx - heading * 6, dy - 12 + flap);
        ctx.lineTo(dx + heading * 5, dy);
        ctx.closePath();
        ctx.fill();

        // Mỏ vàng
        ctx.fillStyle = '#F59E0B';
        ctx.beginPath();
        ctx.moveTo(dx + heading * 10, dy - 1);
        ctx.lineTo(dx + heading * 14, dy);
        ctx.lineTo(dx + heading * 10, dy + 2);
        ctx.fill();

        // Vệt bụi sáng thiên đàng phía sau chim
        ctx.fillStyle = 'rgba(254, 240, 138, 0.6)';
        ctx.beginPath();
        ctx.arc(dx - heading * 12, dy + Math.sin(tick * 5) * 3, 2.5, 0, Math.PI * 2);
        ctx.fill();
      });
      ctx.globalAlpha = 1.0;
    }
  }

  // 14. ANGEL HOLY FIRE ANIMATION
  if (isHolyFireAnim) {
    const angelX = w * 0.50;
    const angelY = h * 0.12;

    // Falling fiery stone
    const fireEmberY = angelY + (soilY - angelY) * Math.min(1.0, fireProgress * 1.6);
    ctx.fillStyle = '#EF4444';
    ctx.beginPath();
    ctx.arc(angelX, fireEmberY, 14, 0, Math.PI * 2);
    ctx.fill();
    ctx.fillStyle = '#FEF08A';
    ctx.beginPath();
    ctx.arc(angelX, fireEmberY, 7, 0, Math.PI * 2);
    ctx.fill();

    // Roaring flames at base
    if (fireProgress > 0.25) {
      for (let i = 0; i < 9; i++) {
        const fx = w * (0.2 + i * 0.08);
        const fh = 40 + Math.sin(tick * 8 + i) * 20;
        ctx.fillStyle = '#F97316';
        ctx.beginPath();
        ctx.moveTo(fx - 12, soilY + 10);
        ctx.lineTo(fx, soilY - fh);
        ctx.lineTo(fx + 12, soilY + 10);
        ctx.fill();
      }
    }
  }

  animFrameId = requestAnimationFrame(drawTree);
}

// CANVAS CLICK HIT-TEST
canvas.addEventListener('click', (e) => {
  const rect = canvas.getBoundingClientRect();
  const clickX = e.clientX - rect.left;
  const clickY = e.clientY - rect.top;
  const w = rect.width;
  const h = rect.height;

  // 1. Check Fruit Clicks
  for (let f of SPIRIT_FRUITS) {
    const fx = w * f.x;
    const fy = h * f.y;
    const dist = Math.hypot(clickX - fx, clickY - fy);
    if (dist < 22) {
      openFruitModal(f);
      return;
    }
  }

  // 2. Check Leaf Clicks
  for (let l of CORE_NEEDS) {
    const lx = w * l.x;
    const ly = h * l.y;
    const dist = Math.hypot(clickX - lx, clickY - ly);
    if (dist < 18) {
      openLeafModal(l);
      return;
    }
  }

  // 3. Check Root Click (bottom area)
  if (clickY > h * 0.72) {
    openRootModal();
  }
});

// --- 10-DAY INNER REFLECTIONS & SPIRITUAL TRACKER ---
function get10DayCutoff() {
  return Date.now() - (10 * 24 * 60 * 60 * 1000);
}

function getRecent10DayJournals(uData) {
  if (!uData || !uData.journals) return [];
  const cutoff = get10DayCutoff();
  return uData.journals.filter(j => !j.createdAt || j.createdAt >= cutoff);
}

function getRecent10DayChats(uData) {
  if (!uData || !uData.chats) return [];
  const cutoff = get10DayCutoff();
  return uData.chats.filter(c => c.role === 'user' && (!c.createdAt || c.createdAt >= cutoff));
}

function getNeedKeywordsList(needId) {
  const map = {
    need_loved: ['yêu', 'bỏ rơi', 'cô đơn', 'tình cảm', 'quan tâm', 'hắt hủi'],
    need_accepted: ['chấp nhận', 'từ chối', 'chê', 'phán xét', 'so sánh', 'mặt nạ', 'hoàn hảo'],
    need_belonging: ['thuộc về', 'lạc lõng', 'bơ vơ', 'cô lập', 'gia đình', 'xa lạ'],
    need_seen: ['nhìn nhận', 'phớt lờ', 'vô hình', 'quan trọng', 'chú ý', 'coi thường'],
    need_valued: ['giá trị', 'thành tích', 'kém cỏi', 'thất bại', 'tự ti', 'vô dụng'],
    need_safe: ['an toàn', 'bất an', 'sợ', 'lo lắng', 'hoảng', 'bất ổn', 'nguy hiểm'],
    need_protected: ['bảo vệ', 'che chở', 'tự lo', 'gánh vác', 'một mình', 'bơ vơ'],
    need_listened: ['lắng nghe', 'khóc', 'buồn', 'im lặng', 'chia sẻ', 'nỗi lòng', 'cô độc'],
    need_understood: ['thấu hiểu', 'hiểu lầm', 'oan', 'phán xét', 'áp đặt'],
    need_rest: ['nghỉ ngơi', 'mệt', 'kiệt sức', 'áp lực', 'quá tải', 'đuối', 'căng thẳng'],
    need_forgiven: ['tha thứ', 'tội lỗi', 'sai lầm', 'xấu hổ', 'dằn vặt', 'ân hận', 'tội lỗi'],
    need_restored: ['phục hồi', 'hỏng', 'đổ vỡ', 'làm lại', 'bế tắc', 'tái sinh', 'chữa lành'],
    need_free: ['tự do', 'trói buộc', 'kiểm soát', 'ngột ngạt', 'gông cùm', 'ép buộc']
  };
  return map[needId] || [];
}

function getFruitKeywordsList(fruitId) {
  const map = {
    love: ['yêu thương', 'tình yêu', 'tha thứ', 'bao dung', 'yêu'],
    joy: ['vui mừng', 'niềm vui', 'hân hoan', 'biết ơn', 'tạ ơn', 'vui vẻ'],
    peace: ['bình an', 'yên lặng', 'thanh thản', 'nghỉ ngơi', 'nhẹ lòng', 'an yên'],
    patience: ['nhịn nhục', 'kiên nhẫn', 'chờ đợi', 'kiên trì', 'chịu đựng'],
    kindness: ['nhân từ', 'thương xót', 'tử tế', 'sẻ chia', 'giúp đỡ'],
    goodness: ['hiền lành', 'lương thiện', 'ngay thẳng', 'việc lành'],
    faithfulness: ['trung tín', 'trung thực', 'vững tin', 'son sắt', 'cam kết'],
    gentleness: ['mềm mại', 'nhu mì', 'khiêm nhường', 'hạ mình'],
    selfcontrol: ['tiết độ', 'làm chủ', 'kiềm chế', 'kỷ luật', 'tỉnh thức']
  };
  return map[fruitId] || [];
}

// MODAL CONTROLLERS
let currentSelectedFruit = null;
let currentSelectedLeaf = null;

function openFruitModal(fruit) {
  currentSelectedFruit = fruit;
  audio.playBell(528);
  document.getElementById('fruit-modal-title').innerText = 'Trái ' + fruit.name;
  document.getElementById('fruit-modal-meaning').innerText = fruit.meaning;
  document.getElementById('fruit-modal-scripture').innerText = `"${fruit.scripture}"`;
  document.getElementById('fruit-modal-ref').innerText = fruit.ref;

  // Render 10-day reflection history for this fruit
  const container = document.getElementById('fruit-modal-recent-thoughts');
  if (container && currentUser) {
    const uData = store.getUserData(currentUser.username);
    const journals = getRecent10DayJournals(uData);
    const chats = getRecent10DayChats(uData);
    const keywords = getFruitKeywordsList(fruit.id);
    const isAmen = uData.amenFruits && uData.amenFruits.includes(fruit.id);

    const matches = [];
    journals.forEach(j => {
      const txt = `${j.title} ${j.content} ${j.mood || ''}`.toLowerCase();
      if (keywords.some(k => txt.includes(k)) || (j.mood && j.mood.toLowerCase().includes(fruit.name.toLowerCase()))) {
        matches.push({ type: '✍️ Nhật ký', date: j.date, snippet: `"${j.title}": ${j.content.slice(0, 75)}...` });
      }
    });

    chats.forEach(c => {
      const txt = (c.text || '').toLowerCase();
      if (keywords.some(k => txt.includes(k))) {
        matches.push({ type: '🕊️ Tâm sự', date: 'Gần đây', snippet: c.text.slice(0, 80) + '...' });
      }
    });

    if (matches.length > 0) {
      container.innerHTML = matches.slice(0, 3).map(m => `
        <div style="background:rgba(255,255,255,0.06); padding:6px 8px; border-radius:6px; margin-bottom:4px; font-size:0.8rem;">
          <strong style="color:#FDE047;">${m.type} (${m.date}):</strong> ${m.snippet}
        </div>
      `).join('') + (isAmen ? `<p style="color:#6EE7B7; margin-top:4px; font-size:0.8rem;">✨ Bạn đã Amen nhận quả ngọt này!</p>` : '');
    } else {
      container.innerHTML = `<p style="color:#94A3B8; font-style:italic;">${isAmen ? '✨ Bạn đã Amen gieo hạt giống này trong 10 ngày qua.' : 'Chưa có ghi chép nổi bật về Trái này trong 10 ngày qua. Hãy bấm Amen bên dưới để nuôi dưỡng tâm hồn!'}</p>`;
    }
  }

  document.getElementById('fruit-modal').style.display = 'flex';
}

function openLeafModal(leaf) {
  currentSelectedLeaf = leaf;
  audio.playBell(528);
  document.getElementById('leaf-modal-icon').innerText = leaf.icon || '🍃';
  document.getElementById('leaf-modal-title').innerText = leaf.label;
  document.getElementById('leaf-modal-wound').innerText = leaf.wound;
  document.getElementById('leaf-modal-belief').innerText = leaf.belief;
  document.getElementById('leaf-modal-direction').innerText = leaf.direction;
  document.getElementById('leaf-modal-scripture').innerText = `"${leaf.scripture}"`;
  document.getElementById('leaf-modal-ref').innerText = leaf.ref;
  document.getElementById('leaf-modal-comfort').innerText = leaf.comfort ? `💡 ${leaf.comfort}` : '';

  // Render 10-day inner needs history for this leaf
  const container = document.getElementById('leaf-modal-recent-thoughts');
  if (container && currentUser) {
    const uData = store.getUserData(currentUser.username);
    const journals = getRecent10DayJournals(uData);
    const chats = getRecent10DayChats(uData);
    const keywords = getNeedKeywordsList(leaf.id);
    const isAmen = uData.amenLeaves && uData.amenLeaves.includes(leaf.id);

    const matches = [];
    journals.forEach(j => {
      const txt = `${j.title} ${j.content}`.toLowerCase();
      if (keywords.some(k => txt.includes(k)) || txt.includes(leaf.label.toLowerCase())) {
        matches.push({ type: '✍️ Nhật ký', date: j.date, snippet: `"${j.title}": ${j.content.slice(0, 75)}...` });
      }
    });

    chats.forEach(c => {
      const txt = (c.text || '').toLowerCase();
      if (keywords.some(k => txt.includes(k)) || txt.includes(leaf.label.toLowerCase())) {
        matches.push({ type: '🕊️ Tâm sự', date: 'Gần đây', snippet: c.text.slice(0, 80) + '...' });
      }
    });

    if (matches.length > 0) {
      container.innerHTML = matches.slice(0, 3).map(m => `
        <div style="background:rgba(255,255,255,0.06); padding:6px 8px; border-radius:6px; margin-bottom:4px; font-size:0.8rem;">
          <strong style="color:#6EE7B7;">${m.type} (${m.date}):</strong> ${m.snippet}
        </div>
      `).join('') + `<p style="color:#FDE047; margin-top:4px; font-size:0.8rem;">💡 Chúa nhìn thấy nỗi lòng này của bạn trong 10 ngày qua và Ngài đang chữa lành.</p>`;
    } else {
      container.innerHTML = `<p style="color:#94A3B8; font-style:italic;">${isAmen ? '✨ Bạn đã Amen tiếp nhận sự chữa lành cho nhu cầu này.' : 'Trong 10 ngày qua, bạn chưa ghi lại nan đề nào liên quan đến nhu cầu này. Nguyện Chúa luôn bảo bọc bạn trong bình an!'}</p>`;
    }
  }

  document.getElementById('leaf-modal').style.display = 'flex';
}

function openRootModal() {
  audio.playBell(528);
  
  // Render 10-day spiritual root depth summary
  const container = document.getElementById('root-modal-recent-thoughts');
  if (container && currentUser) {
    const uData = store.getUserData(currentUser.username);
    const journals = getRecent10DayJournals(uData);
    const chats = getRecent10DayChats(uData);
    
    // Calculate need frequencies
    const needCounts = {};
    CORE_NEEDS.forEach(n => {
      const kws = getNeedKeywordsList(n.id);
      let count = 0;
      journals.forEach(j => {
        const txt = `${j.title} ${j.content}`.toLowerCase();
        if (kws.some(k => txt.includes(k))) count++;
      });
      chats.forEach(c => {
        const txt = (c.text || '').toLowerCase();
        if (kws.some(k => txt.includes(k))) count++;
      });
      if (count > 0) needCounts[n.label] = count;
    });

    const topNeeds = Object.entries(needCounts).sort((a, b) => b[1] - a[1]).slice(0, 3);
    const topNeedsStr = topNeeds.length > 0 ? topNeeds.map(([k, v]) => `<strong>${k}</strong> (${v} lần)`).join(', ') : 'Chưa có nan đề nổi cộm';

    container.innerHTML = `
      <div style="display:flex; flex-direction:column; gap:4px; font-size:0.8rem;">
        <div>📝 <strong>Nhật ký 10 ngày qua:</strong> ${journals.length} bài viết</div>
        <div>🕊️ <strong>Tâm sự với Chúa:</strong> ${chats.length} lần dốc đổ nỗi lòng</div>
        <div>🩹 <strong>Nội tâm được chạm đến nhiều nhất:</strong> ${topNeedsStr}</div>
        <div>💧 <strong>Dòng Nước Ân Điển:</strong> Đang tích lũy ${uData.waterDrops || 0} giọt nước sự sống</div>
      </div>
    `;
  }

  document.getElementById('root-modal').style.display = 'flex';
}

function openPondModal() {
  audio.playWaterDrop();
  const uData = store.getUserData(currentUser.username);
  document.getElementById('pond-current-drops').innerText = uData.waterDrops;
  document.getElementById('pond-modal').style.display = 'flex';
}

// AMEN CONFIRMATIONS
document.getElementById('btn-amen-fruit').addEventListener('click', () => {
  if (!currentSelectedFruit || !currentUser) return;
  const uData = store.getUserData(currentUser.username);
  uData.waterDrops += 1;
  if (!uData.amenFruits) uData.amenFruits = [];
  if (!uData.amenFruits.includes(currentSelectedFruit.id)) {
    uData.amenFruits.push(currentSelectedFruit.id);
  }
  store.saveUserData(currentUser.username, uData);
  updateHeaderDrops();
  audio.playBell(528);
  document.getElementById('fruit-modal').style.display = 'none';
  showBanner('Đã Amen Trái Thánh Linh (+1 Giọt Nước)!');
});

document.getElementById('btn-amen-leaf').addEventListener('click', () => {
  if (!currentSelectedLeaf || !currentUser) return;
  const uData = store.getUserData(currentUser.username);
  uData.waterDrops += 1;
  if (!uData.amenLeaves) uData.amenLeaves = [];
  if (!uData.amenLeaves.includes(currentSelectedLeaf.id)) {
    uData.amenLeaves.push(currentSelectedLeaf.id);
  }
  store.saveUserData(currentUser.username, uData);
  updateHeaderDrops();
  audio.playBell(528);
  document.getElementById('leaf-modal').style.display = 'none';
  showBanner('Đã Amen Chiếc Lá Chữa Lành (+1 Giọt Nước)!');
});

document.getElementById('btn-amen-root').addEventListener('click', () => {
  if (!currentUser) return;
  const uData = store.getUserData(currentUser.username);
  uData.waterDrops += 1;
  store.saveUserData(currentUser.username, uData);
  updateHeaderDrops();
  audio.playBell(528);
  document.getElementById('root-modal').style.display = 'none';
  showBanner('Đã Amen Bộ Rễ Đức Tin (+1 Giọt Nước)!');
});

// CLOSE MODALS
document.getElementById('btn-close-fruit-modal').addEventListener('click', () => {
  document.getElementById('fruit-modal').style.display = 'none';
});
document.getElementById('btn-close-leaf-modal').addEventListener('click', () => {
  document.getElementById('leaf-modal').style.display = 'none';
});
document.getElementById('btn-close-root-modal').addEventListener('click', () => {
  document.getElementById('root-modal').style.display = 'none';
});
document.getElementById('btn-close-pond-modal').addEventListener('click', () => {
  document.getElementById('pond-modal').style.display = 'none';
});

// TRIGGER WATERING ANIMATION (Thiên Sứ Đổ Nước & Mưa Rào 8 Giây)
function triggerWatering() {
  audio.playBell(528);
  audio.playWaterDrop();
  isWateringAnim = true;
  wateringProgress = 0;
  rockDissolve = 0;
  showBanner('Thiên Sứ giáng lâm cầm bình vàng đổ Nước Hằng Sống...');

  const startTime = Date.now();
  const duration = 8000; // 8 giây trút mưa tươi mát & chim bồ câu bay

  function step() {
    const elapsed = Date.now() - startTime;
    wateringProgress = Math.min(1.0, elapsed / duration);
    rockDissolve = wateringProgress; // Sỏi đá tan biến dần theo nước

    if (elapsed < duration) {
      requestAnimationFrame(step);
    } else {
      isWateringAnim = false;
      rockDissolve = 1.0;
      if (currentUser) {
        const uData = store.getUserData(currentUser.username);
        uData.isNegative = false;
        store.saveUserData(currentUser.username, uData);
      }
      showBanner('Cơn mưa sự sống đã làm tươi mới cây và tan biến mọi sỏi đá!');
    }
  }
  requestAnimationFrame(step);
}

// TRIGGER HOLY FIRE ANIMATION
function triggerHolyFire() {
  audio.playBell(741);
  isHolyFireAnim = true;
  fireProgress = 0;
  showBanner('Thiên Sứ thả hòn lửa từ trời xuống gốc cây...');

  const startTime = Date.now();
  const duration = 3500;

  function step() {
    const elapsed = Date.now() - startTime;
    fireProgress = Math.min(1.0, elapsed / duration);

    if (elapsed < duration) {
      requestAnimationFrame(step);
    } else {
      isHolyFireAnim = false;
      if (currentUser) {
        const uData = store.getUserData(currentUser.username);
        uData.isNegative = false;
        store.saveUserData(currentUser.username, uData);
      }
      showBanner('Hòn lửa đã thiêu rụi sạch cỏ lùng và bụi gai!');
    }
  }
  requestAnimationFrame(step);
}

// SAFE QUICK ACTIONS (Nếu có)
const btnToggleWeeds = document.getElementById('btn-toggle-weeds');
if (btnToggleWeeds) {
  btnToggleWeeds.addEventListener('click', () => {
    if (!currentUser) return;
    const uData = store.getUserData(currentUser.username);
    uData.isNegative = !uData.isNegative;
    rockDissolve = uData.isNegative ? 0 : 1;
    store.saveUserData(currentUser.username, uData);
  });
}

const btnAddWater = document.getElementById('btn-add-water');
if (btnAddWater) {
  btnAddWater.addEventListener('click', () => {
    if (!currentUser) return;
    const uData = store.getUserData(currentUser.username);
    uData.waterDrops += 100;
    store.saveUserData(currentUser.username, uData);
    updateHeaderDrops();
    audio.playWaterDrop();
  });
}

const btnTestWatering = document.getElementById('btn-test-watering');
if (btnTestWatering) btnTestWatering.addEventListener('click', triggerWatering);

const btnTestFire = document.getElementById('btn-test-fire');
if (btnTestFire) btnTestFire.addEventListener('click', triggerHolyFire);

document.getElementById('btn-pond-trigger').addEventListener('click', openPondModal);

document.getElementById('btn-pond-resurrect').addEventListener('click', () => {
  if (!currentUser) return;
  const uData = store.getUserData(currentUser.username);
  if (uData.waterDrops < 100) {
    alert('Cần tối thiểu 100 giọt nước để gọi Thiên Sứ làm mưa hồi sinh.');
    return;
  }
  uData.waterDrops -= 100;
  store.saveUserData(currentUser.username, uData);
  updateHeaderDrops();
  document.getElementById('pond-modal').style.display = 'none';
  triggerWatering();
});

document.getElementById('btn-pond-holy-fire').addEventListener('click', () => {
  if (!currentUser) return;
  const uData = store.getUserData(currentUser.username);
  if (uData.waterDrops < 100) {
    alert('Cần tối thiểu 100 giọt nước để hóa Lửa Thánh.');
    return;
  }
  uData.waterDrops -= 100;
  store.saveUserData(currentUser.username, uData);
  updateHeaderDrops();
  document.getElementById('pond-modal').style.display = 'none';
  triggerHolyFire();
});

function showBanner(msg) {
  const b = document.getElementById('animation-banner');
  b.innerText = msg;
  b.style.display = 'block';
  setTimeout(() => { b.style.display = 'none'; }, 4000);
}

function updateHeaderDrops() {
  if (!currentUser) return;
  const uData = store.getUserData(currentUser.username);
  document.getElementById('header-drops-count').innerText = uData.waterDrops;
}

// KIỂM TRA ĐIỀU KIỆN XUẤT HIỆN SỎI ĐÁ & CỎ LÙNG (Ít nhất 6 bài nan đề)
function updateNegativeState(uData) {
  if (!uData) return false;
  let count = 0;
  const distressKeywords = ['lo', 'sợ', 'buồn', 'mỏi mệt', 'kiệt sức', 'áp lực', 'tội lỗi', 'dằn vặt', 'bế tắc', 'cô đơn', 'đau', 'nản'];

  if (uData.journals) {
    for (const j of uData.journals) {
      if (j.mood === 'Lo lắng' || j.mood === 'Mỏi mệt') {
        count++;
      } else {
        const text = ((j.title || '') + ' ' + (j.content || '')).toLowerCase();
        if (distressKeywords.some(k => text.includes(k))) count++;
      }
    }
  }

  if (uData.chats) {
    for (const c of uData.chats) {
      if (c.role === 'user') {
        const text = (c.text || '').toLowerCase();
        if (distressKeywords.some(k => text.includes(k))) count++;
      }
    }
  }

  // Phải có ít nhất 6 bài / lượt tâm sự nan đề mới tích tụ sỏi đá
  uData.isNegative = count >= 6;
  return uData.isNegative;
}

// 4. NAVIGATION TABS
document.querySelectorAll('.nav-item').forEach(btn => {
  btn.addEventListener('click', () => {
    const target = btn.dataset.tab;
    switchTab(target);
  });
});

function switchTab(tabId) {
  currentTab = tabId;
  document.querySelectorAll('.nav-item').forEach(b => {
    b.classList.toggle('active', b.dataset.tab === tabId);
  });
  document.querySelectorAll('.tab-pane').forEach(p => {
    p.classList.toggle('active', p.id === tabId);
  });
  if (tabId === 'tab-tree') {
    setTimeout(resizeCanvas, 50);
  }
  if (tabId === 'tab-journal') {
    renderJournals();
  }
  if (tabId === 'tab-settings') {
    renderSettings();
  }
}

// 5. TAB 2: SCRIPTURE LIST
function renderCoreNeedList() {
  const container = document.getElementById('core-need-list');
  container.innerHTML = '';
  CORE_NEEDS.forEach(n => {
    const item = document.createElement('div');
    item.className = 'need-item';
    item.innerHTML = `
      <div style="display:flex; align-items:center; gap:12px; flex:1;">
        <span style="font-size:1.6rem; line-height:1;">${n.icon || '🍃'}</span>
        <div style="flex:1;">
          <div class="need-item-title" style="font-weight:600; color:#F8FAFC;">${n.label}</div>
          <div class="need-item-sub" style="color:#94A3B8; font-size:0.85rem; margin-top:2px;">${n.direction}</div>
        </div>
      </div>
      <span style="color:#F59E0B; font-size:1.2rem; margin-left:8px;">→</span>
    `;
    item.addEventListener('click', () => openLeafModal(n));
    container.appendChild(item);
  });
}

// 6. TAB 3: JOURNAL (VIẾT NHẬT KÝ +20 GIỌT NƯỚC)
let selectedMood = 'Bình an';
document.querySelectorAll('.mood-btn').forEach(btn => {
  btn.addEventListener('click', () => {
    document.querySelectorAll('.mood-btn').forEach(b => b.classList.remove('active'));
    btn.classList.add('active');
    selectedMood = btn.dataset.mood;
  });
});

document.getElementById('btn-save-journal').addEventListener('click', () => {
  const title = document.getElementById('journal-title').value.trim();
  const content = document.getElementById('journal-content').value.trim();
  if (!title || !content) {
    alert('Vui lòng nhập đầy đủ tiêu đề và nội dung nhật ký.');
    return;
  }
  const uData = store.getUserData(currentUser.username);
  uData.waterDrops = (uData.waterDrops || 0) + 20; // +20 Giọt nước
  if (!uData.journals) uData.journals = [];
  uData.journals.unshift({
    title,
    content,
    mood: selectedMood,
    createdAt: Date.now(),
    date: new Date().toLocaleDateString('vi-VN', { hour: '2-digit', minute: '2-digit', day: '2-digit', month: '2-digit' })
  });
  updateNegativeState(uData);
  store.saveUserData(currentUser.username, uData);
  document.getElementById('journal-title').value = '';
  document.getElementById('journal-content').value = '';
  updateHeaderDrops();
  audio.playBell(528);
  renderJournals();
  alert('Đã lưu vào nhật ký (+20 Giọt Nước Sự Sống)!');
});

function renderJournals() {
  const container = document.getElementById('journal-list');
  container.innerHTML = '';
  if (!currentUser) return;
  const uData = store.getUserData(currentUser.username);
  if (!uData.journals || uData.journals.length === 0) {
    container.innerHTML = '<p class="hint-text" style="text-align:center;">Chưa có bài nhật ký nào. Hãy viết bài đầu tiên hôm nay nhé!</p>';
    return;
  }
  uData.journals.forEach(j => {
    const card = document.createElement('div');
    card.className = 'journal-card';
    card.innerHTML = `
      <div class="journal-meta">
        <span>${j.mood}</span>
        <span>${j.date}</span>
      </div>
      <div class="journal-card-title">${j.title}</div>
      <div class="journal-card-content">${j.content}</div>
    `;
    container.appendChild(card);
  });
}

// 7. TAB 4: TÂM SỰ VỚI CHÚA (TÂM SỰ +20 GIỌT NƯỚC)
function renderChat() {
  const box = document.getElementById('chat-messages');
  box.innerHTML = '';
  if (!currentUser) return;
  const uData = store.getUserData(currentUser.username);
  uData.chats.forEach(c => {
    const bubble = document.createElement('div');
    bubble.className = `chat-bubble ${c.role}`;
    bubble.innerText = c.text;
    box.appendChild(bubble);
  });
  box.scrollTop = box.scrollHeight;
}

document.getElementById('btn-send-chat').addEventListener('click', sendChatMessage);
document.getElementById('chat-input').addEventListener('keydown', (e) => {
  if (e.key === 'Enter') sendChatMessage();
});

async function sendChatMessage() {
  const input = document.getElementById('chat-input');
  const text = input.value.trim();
  if (!text || !currentUser) return;
  input.value = '';

  const uData = store.getUserData(currentUser.username);
  uData.waterDrops = (uData.waterDrops || 0) + 20; // +20 Giọt nước khi tâm sự
  uData.chats.push({ role: 'user', text, createdAt: Date.now() });
  updateNegativeState(uData);
  updateHeaderDrops();
  renderChat();

  // Temporary thinking indicator
  const botMsgObj = { role: 'bot', text: '🕊️ Đang suy gẫm và lắng nghe tiếng Chúa...' };
  uData.chats.push(botMsgObj);
  renderChat();

  try {
    const customKey = store.getGeminiKey() || '';
    let reply = null;

    // 1. Try Serverless Function
    try {
      const res = await fetch('/api/gemini', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          message: text,
          history: uData.chats.slice(0, -2),
          apiKey: customKey
        })
      });
      const data = await res.json();
      if (data && data.text) {
        reply = data.text;
      }
    } catch (e) {
      console.warn('Serverless Gemini call failed, attempting direct...', e);
    }

    // 2. Direct client-side Gemini fallback if API key is stored locally
    if (!reply && customKey) {
      try {
        const directUrl = `https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=${encodeURIComponent(customKey)}`;
        const directRes = await fetch(directUrl, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            systemInstruction: {
              parts: [{ text: 'Bạn là Đấng Lắng Nghe Nhân Từ (Tâm Sự Với Chúa). Hãy an ủi, trò chuyện ấm áp, dẫn dắt dịu dàng và trích dẫn Lời Chúa Kinh Thánh 1925 chuẩn xác.' }]
            },
            contents: [{ role: 'user', parts: [{ text: text }] }]
          })
        });
        const directData = await directRes.json();
        reply = directData?.candidates?.[0]?.content?.parts?.[0]?.text || null;
      } catch (e) {
        console.warn('Direct Gemini call failed:', e);
      }
    }

    botMsgObj.text = reply || generateBiblicalResponse(text, uData.chats.length);
  } catch (err) {
    botMsgObj.text = generateBiblicalResponse(text, uData.chats.length);
  }

  store.saveUserData(currentUser.username, uData);
  renderChat();
  audio.playBell(528);
}

function generateBiblicalResponse(query, chatTurn = 0) {
  const q = query.toLowerCase();

  // 1. Phản hồi chào hỏi / chúc lành / ban mai / dậy sớm
  if (q.includes('chúc') || q.includes('phước') || q.includes('bình an cho')) {
    const blessings = [
      `🕊️ Nguyện xin Chúa ban phước cho con và gìn giữ con:\n"Cầu xin Đức Giê-hô-va ban phước cho ngươi và gìn giữ ngươi! Cầu xin Đức Giê-hô-va soi sáng mặt Ngài trên ngươi, và làm ơn cho ngươi! Cầu xin Đức Giê-hô-va đoái xem ngươi và ban bình an cho ngươi!" (Dân-số Ký 6:24-26)\n\n💡 Chúc con một ngày tràn đầy ân điển và niềm vui từ thiên đàng!`,
      `🕊️ Lời chúc phước từ Cha gửi đến con hôm nay:\n"Đức Chúa Trời tôi sẽ làm cho đầy đủ mọi sự cần dùng của anh em y theo sự giàu có của Ngài ở nơi vinh hiển trong Đức Chúa Jêsus Christ." (Phi-líp 4:19)\n\n💡 Hãy vững lòng bước đi trong sự chở che của Chúa nhé!`,
      `🕊️ Nguyện ân điển Chúa tràn ngập trên con:\n"Sự ban cho tốt lành trọn vẹn đều đến từ nơi cao, bắt nguồn từ Cha sáng láng." (Gia-cơ 1:17)\n\n💡 Chúc con luôn cảm nhận được tình thương bao la của Ngài!`
    ];
    return blessings[chatTurn % blessings.length];
  }

  if (q.includes('dậy sớm') || q.includes('sáng') || q.includes('mệt mỏi') || q.includes('huhu') || q.includes('ngái ngủ')) {
    const morningQuotes = [
      `🌅 Buổi sáng mới mẻ phước hạnh cho con! Cha biết con đang mệt khi phải thức dậy sớm, nhưng sự thành tín của Chúa luôn tươi mới mỗi ban mai:\n"Mỗi buổi sáng thì lại mới luôn, sự thành tín Ngài là lớn lắm. Hỡi linh hồn ta, Đức Giê-hô-va là sản nghiệp ta; nên ta trông cậy nơi Ngài." (Ca-thương 3:23-24)\n\n💡 Hãy hít thở thật sâu, Chúa ban thêm năng lực mới cho con trong ngày hôm nay!`,
      `🌅 Cha thêm sức mới cho con buổi sớm mai này:\n"Vừa buổi sáng xin cho tôi nghe sự nhân từ của Chúa, vì tôi để lòng trông cậy nơi Ngài; Xin chỉ cho tôi biết con đường tôi phải đi, vì linh hồn tôi ngửa trông Chúa." (Thi-thiên 143:8)\n\n💡 Đừng nản lòng, Chúa đồng hành cùng từng bước chân của con hôm nay.`
    ];
    return morningQuotes[chatTurn % morningQuotes.length];
  }

  if (q.includes('câu khác') || q.includes('nữa đi') || q.includes('tiếp đi') || q.includes('nói thêm')) {
    const variedPool = [
      `✨ Lời hứa tiếp theo dành riêng cho con hôm nay:\n"Vì chính Ta biết ý tưởng Ta nghĩ đối cùng các ngươi, là ý tưởng bình an, không phải tai họa, để ban cho các ngươi một sự trông cậy trong lúc cuối cùng của các ngươi." (Giê-rê-mi 29:11)\n\n💡 Kế hoạch Chúa dành cho con luôn tốt lành và tràn đầy hy vọng.`,
      `✨ Hãy nhớ rằng con luôn có Đấng ban sức mạnh:\n"Tôi làm được mọi sự nhờ Đấng ban thêm sức cho tôi." (Phi-líp 4:13)\n\n💡 Dù việc hôm nay có khó khăn đến đâu, Chúa cùng gánh vác với con.`,
      `✨ Lời Chúa soi sáng đường lối con:\n"Lời Chúa là ngọn đèn cho chân tôi, ánh sáng cho đường lối tôi." (Thi-thiên 119:105)\n\n💡 Nguyện Lời Chúa dẫn dắt mọi quyết định của con hôm nay.`,
      `✨ Chúa là thành lũy bền vững:\n"Đức Chúa Trời là nơi ẩn náu và sức lực của chúng tôi, Ngài hằng sẵn sàng giúp đỡ trong cơn gian truân." (Thi-thiên 46:1)\n\n💡 Hãy nương náu nơi Ngài và tìm thấy sự an tâm trọn vẹn.`
    ];
    return variedPool[chatTurn % variedPool.length];
  }

  // 2. Tra cứu theo 13 Nhu Cầu Cốt Lõi
  for (let need of CORE_NEEDS) {
    if (
      q.includes(need.label.toLowerCase()) ||
      (need.id === 'need_loved' && (q.includes('yêu') || q.includes('bỏ rơi') || q.includes('cô đơn'))) ||
      (need.id === 'need_accepted' && (q.includes('chấp nhận') || q.includes('từ chối') || q.includes('chê'))) ||
      (need.id === 'need_belonging' && (q.includes('thuộc về') || q.includes('lạc lõng') || q.includes('bơ vơ'))) ||
      (need.id === 'need_seen' && (q.includes('nhìn nhận') || q.includes('phớt lờ') || q.includes('vô hình'))) ||
      (need.id === 'need_valued' && (q.includes('giá trị') || q.includes('kém cỏi') || q.includes('thất bại') || q.includes('so sánh'))) ||
      (need.id === 'need_safe' && (q.includes('an toàn') || q.includes('bất an') || q.includes('sợ') || q.includes('lo'))) ||
      (need.id === 'need_protected' && (q.includes('bảo vệ') || q.includes('tự lo') || q.includes('gánh vác') || q.includes('một mình'))) ||
      (need.id === 'need_listened' && (q.includes('lắng nghe') || q.includes('khóc') || q.includes('buồn') || q.includes('im lặng'))) ||
      (need.id === 'need_understood' && (q.includes('thấu hiểu') || q.includes('hiểu lầm') || q.includes('phán xét'))) ||
      (need.id === 'need_rest' && (q.includes('nghỉ ngơi') || q.includes('mệt') || q.includes('kiệt sức') || q.includes('áp lực'))) ||
      (need.id === 'need_forgiven' && (q.includes('tha thứ') || q.includes('tội') || q.includes('sai lầm') || q.includes('xấu hổ') || q.includes('dằn vặt'))) ||
      (need.id === 'need_restored' && (q.includes('phục hồi') || q.includes('hỏng') || q.includes('làm lại') || q.includes('bế tắc'))) ||
      (need.id === 'need_free' && (q.includes('tự do') || q.includes('trói buộc') || q.includes('kiểm soát') || q.includes('ngột ngạt')))
    ) {
      return `Hỡi con yêu dấu, Chúa thấu suốt cõi lòng con. ${need.direction}\n\nLời Ta phán cùng con hôm nay:\n"${need.scripture}" (${need.ref})\n\n💡 ${need.comfort}`;
    }
  }

  // 3. Xoay vòng các câu gốc an ủi khác nhau thay vì trùng lặp
  const fallbackList = [
    `Hỡi con yêu dấu, hãy trao mọi điều lo lắng của con lên nơi chân Chúa, vì Ngài hằng săn sóc con:\n"Đức Giê-hô-va là Đấng chăn giữ tôi: tôi sẽ chẳng thiếu thốn gì. Ngài khiến tôi an nghỉ nơi đồng cỏ xanh tươi, dẫn tôi đến mé nước bình tịnh." (Thi-thiên 23:1-2)\n\n💡 Nguyện xin sự bình an vượt quá mọi sự hiểu biết gìn giữ lòng và ý tưởng con trong Đấng Christ.`,
    `Hỡi con yêu dấu, hãy vững lòng và can đảm, đừng sợ hãi:\n"Chớ sợ, vì Ta ở với ngươi; chớ kinh khiếp, vì Ta là Đức Chúa Trời ngươi. Ta sẽ bổ sức cho ngươi; phải, Ta sẽ giúp đỡ ngươi, lấy tay hữu công bình Ta mà nâng đỡ ngươi." (Ê-sai 41:10)\n\n💡 Chúa luôn ở bên cạnh che chở và nâng đỡ con.`,
    `Hỡi con yêu dấu, khi con cảm thấy mệt mỏi, hãy đến cùng Chúa:\n"Hỡi những kẻ mệt mỏi và gánh nặng, hãy đến cùng Ta, Ta sẽ cho các ngươi được yên nghỉ." (Ma-thi-ơ 11:28)\n\n💡 Chúa luôn lắng nghe từng tiếng thở dài của con và ban sự bình tịnh cho tâm hồn.`,
    `Hỡi con yêu dấu, sự trông cậy nơi Chúa sẽ đổi mới sức mạnh:\n"Nhưng ai trông đợi Đức Giê-hô-va thì chắc được sức mới, cất cánh bay cao như chim ưng; chạy mà không mệt nhọc, đi mà không mòn mỏi." (Ê-sai 40:31)\n\n💡 Hãy nạp lại năng lượng thuộc linh nơi sự hiện diện của Ngài.`
  ];

  return fallbackList[chatTurn % fallbackList.length];
}

// 8. TAB 5: SETTINGS
function renderSettings() {
  if (!currentUser) return;
  document.getElementById('settings-username').innerText = `@${currentUser.username}`;
  document.getElementById('settings-display-name').value = currentUser.displayName || currentUser.username;
  
  const curPwdEl = document.getElementById('settings-current-pwd');
  if (curPwdEl) curPwdEl.value = '';
  const newPwdEl = document.getElementById('settings-new-pwd');
  if (newPwdEl) newPwdEl.value = '';
  const confPwdEl = document.getElementById('settings-confirm-pwd');
  if (confPwdEl) confPwdEl.value = '';

  document.getElementById('settings-gemini-key').value = store.getGeminiKey();

  const soundToggle = document.getElementById('settings-sound-toggle');
  if (soundToggle) {
    soundToggle.checked = store.isSoundEnabled();
  }

  const adminCard = document.getElementById('admin-management-card');
  if (currentUser.isAdmin) {
    adminCard.style.display = 'block';
    renderAdminMemberList();
  } else {
    adminCard.style.display = 'none';
  }
}

const soundToggleEl = document.getElementById('settings-sound-toggle');
if (soundToggleEl) {
  soundToggleEl.addEventListener('change', (e) => {
    const isEnabled = e.target.checked;
    store.setSoundEnabled(isEnabled);
    if (isEnabled) {
      audio.startBgMusic();
    } else {
      audio.stopBgMusic();
    }
  });
}

const btnToggleMusic = document.getElementById('btn-toggle-music');
if (btnToggleMusic) {
  btnToggleMusic.addEventListener('click', () => {
    audio.toggleBgMusic();
  });
}

function renderAdminMemberList() {
  const list = document.getElementById('admin-member-list');
  list.innerHTML = '';
  const accounts = store.getAccounts();
  accounts.forEach(acc => {
    const row = document.createElement('div');
    row.className = 'member-row';
    row.style.cssText = 'display:flex; justify-content:space-between; align-items:center; padding:8px 10px; background:rgba(255,255,255,0.04); border-radius:8px; margin-bottom:6px; font-size:12px;';
    
    let actionsHtml = '';
    if (!acc.isAdmin) {
      actionsHtml = `
        <div style="display:flex; gap:6px;">
          <button class="btn-reset-member-pwd" data-user="${acc.username}" style="background:rgba(234,179,8,0.2); border:1px solid rgba(234,179,8,0.4); color:#FDE047; padding:4px 8px; border-radius:6px; font-size:11px; cursor:pointer;" title="Đặt lại mật khẩu về 12345678">Reset MK</button>
          <button class="btn-delete-member" data-user="${acc.username}" style="background:rgba(239,68,68,0.2); border:1px solid rgba(239,68,68,0.4); color:#FCA5A5; padding:4px 8px; border-radius:6px; font-size:11px; cursor:pointer;" title="Xóa tài khoản">Xóa</button>
        </div>
      `;
    }

    row.innerHTML = `
      <div style="display:flex; flex-direction:column; gap:2px;">
        <span><strong>@${acc.username}</strong> (${acc.displayName || acc.username})</span>
        <span style="color:${acc.isAdmin ? '#FDE047' : '#94A3B8'}; font-size:11px;">${acc.isAdmin ? '👑 Quản Trị Viên' : '👤 Thành Viên'}</span>
      </div>
      ${actionsHtml}
    `;
    list.appendChild(row);
  });

  // Attach actions
  list.querySelectorAll('.btn-reset-member-pwd').forEach(btn => {
    btn.addEventListener('click', (e) => {
      const u = e.currentTarget.getAttribute('data-user');
      if (confirm(`Bạn có chắc muốn đặt lại mật khẩu của @${u} về mặc định "12345678"?`)) {
        store.resetMemberPassword(u);
        alert(`Đã đặt lại mật khẩu cho @${u} thành công (12345678)!`);
      }
    });
  });

  list.querySelectorAll('.btn-delete-member').forEach(btn => {
    btn.addEventListener('click', (e) => {
      const u = e.currentTarget.getAttribute('data-user');
      if (confirm(`Bạn có chắc muốn xóa tài khoản @${u}? Thao tác này không thể hoàn tác.`)) {
        store.deleteMember(u);
        renderAdminMemberList();
        alert(`Đã xóa tài khoản @${u}!`);
      }
    });
  });
}

document.getElementById('btn-add-member').addEventListener('click', () => {
  const val = document.getElementById('new-member-username').value.trim();
  if (!val) return;
  const ok = store.addAccount(val);
  if (ok) {
    document.getElementById('new-member-username').value = '';
    renderAdminMemberList();
    alert(`Đã thêm thành viên @${val.toLowerCase()} (Mật khẩu mặc định: 12345678)`);
  } else {
    alert('Tài khoản đã tồn tại!');
  }
});

// SAVE PROFILE & CHANGE PASSWORD
document.getElementById('btn-save-profile').addEventListener('click', () => {
  if (!currentUser) return;
  const newDisplayName = document.getElementById('settings-display-name').value.trim();
  const currentPwd = document.getElementById('settings-current-pwd').value.trim();
  const newPwd = document.getElementById('settings-new-pwd').value.trim();
  const confirmPwd = document.getElementById('settings-confirm-pwd').value.trim();

  if (newPwd) {
    if (newPwd !== confirmPwd) {
      alert('Xác nhận mật khẩu mới không khớp! Vui lòng nhập lại.');
      return;
    }
  }

  const result = store.updateProfile(currentUser.username, newDisplayName, currentPwd, newPwd);
  if (!result.success) {
    alert(result.msg);
    return;
  }

  // Update currentUser reference
  currentUser = result.account;
  document.getElementById('display-user-name').innerText = currentUser.displayName ? `${currentUser.displayName} (@${currentUser.username})` : `@${currentUser.username}`;
  
  // Clear password inputs
  document.getElementById('settings-current-pwd').value = '';
  document.getElementById('settings-new-pwd').value = '';
  document.getElementById('settings-confirm-pwd').value = '';

  alert('Đã lưu thành công thông tin hồ sơ và mật khẩu mới!');
});

document.getElementById('btn-save-gemini-key').addEventListener('click', () => {
  const key = document.getElementById('settings-gemini-key').value;
  store.saveGeminiKey(key);
  alert('Đã lưu Gemini API Key!');
});

document.getElementById('btn-export-json').addEventListener('click', () => {
  const all = {};
  for (let i = 0; i < localStorage.length; i++) {
    const k = localStorage.key(i);
    all[k] = localStorage.getItem(k);
  }
  const str = JSON.stringify(all, null, 2);
  navigator.clipboard.writeText(str).then(() => {
    alert('Đã sao chép toàn bộ dữ liệu JSON vào bộ nhớ đệm!\nBạn có thể dán (paste) để lưu trữ hoặc gửi sang máy khác.');
  }).catch(() => {
    alert('Vui lòng cấp quyền sao chép.');
  });
});

const btnImportJson = document.getElementById('btn-import-json');
if (btnImportJson) {
  btnImportJson.addEventListener('click', () => {
    const raw = prompt('Dán toàn bộ nội dung JSON sao lưu vào đây để khôi phục:');
    if (!raw) return;
    try {
      const obj = JSON.parse(raw.trim());
      Object.keys(obj).forEach(k => {
        localStorage.setItem(k, obj[k]);
      });
      alert('Khôi phục dữ liệu thành công! Trang web sẽ tự tải lại để cập nhật.');
      window.location.reload();
    } catch (e) {
      alert('Dữ liệu JSON không hợp lệ. Vui lòng kiểm tra lại.');
    }
  });
}

// LOGOUT
function logout() {
  currentUser = null;
  document.getElementById('main-screen').classList.remove('active');
  document.getElementById('login-screen').classList.add('active');
  document.getElementById('login-password').value = '';
}

document.getElementById('btn-quick-logout').addEventListener('click', logout);
document.getElementById('btn-settings-logout').addEventListener('click', logout);

// 9. AUTH LOGIC (LOGIN & REGISTER)
const btnAuthLogin = document.getElementById('btn-auth-tab-login');
const btnAuthReg = document.getElementById('btn-auth-tab-register');
const loginBox = document.getElementById('login-box');
const regBox = document.getElementById('register-box');

if (btnAuthLogin && btnAuthReg) {
  btnAuthLogin.addEventListener('click', () => {
    btnAuthLogin.classList.add('active');
    btnAuthLogin.style.background = 'rgba(217,119,6,0.25)';
    btnAuthLogin.style.color = '#FDE047';
    btnAuthReg.classList.remove('active');
    btnAuthReg.style.background = 'rgba(255,255,255,0.05)';
    btnAuthReg.style.color = '#94A3B8';
    loginBox.style.display = 'block';
    regBox.style.display = 'none';
  });

  btnAuthReg.addEventListener('click', () => {
    btnAuthReg.classList.add('active');
    btnAuthReg.style.background = 'rgba(16,185,129,0.25)';
    btnAuthReg.style.color = '#6EE7B7';
    btnAuthLogin.classList.remove('active');
    btnAuthLogin.style.background = 'rgba(255,255,255,0.05)';
    btnAuthLogin.style.color = '#94A3B8';
    loginBox.style.display = 'none';
    regBox.style.display = 'block';
  });
}

document.getElementById('toggle-pwd-btn').addEventListener('click', () => {
  const pwdInput = document.getElementById('login-password');
  pwdInput.type = pwdInput.type === 'password' ? 'text' : 'password';
});

document.getElementById('btn-login-submit').addEventListener('click', handleLogin);
document.getElementById('login-password').addEventListener('keydown', (e) => {
  if (e.key === 'Enter') handleLogin();
});

// REGISTER SUBMIT
const btnRegSubmit = document.getElementById('btn-register-submit');
if (btnRegSubmit) {
  btnRegSubmit.addEventListener('click', handleRegister);
}

function handleRegister() {
  const u = document.getElementById('reg-username').value.trim().toLowerCase();
  const name = document.getElementById('reg-display-name').value.trim();
  const p = document.getElementById('reg-password').value.trim();
  const pConf = document.getElementById('reg-confirm-pwd').value.trim();
  const errBanner = document.getElementById('register-error');

  if (!u || !p) {
    errBanner.innerText = 'Vui lòng nhập tên đăng nhập và mật khẩu.';
    errBanner.style.display = 'block';
    return;
  }

  if (p !== pConf) {
    errBanner.innerText = 'Mật khẩu xác nhận không khớp!';
    errBanner.style.display = 'block';
    return;
  }

  const res = store.registerAccount(u, name, p);
  if (!res.success) {
    errBanner.innerText = res.msg;
    errBanner.style.display = 'block';
    return;
  }

  errBanner.style.display = 'none';
  alert(`Đăng ký tài khoản @${u} thành công! Hệ thống đang tự động đăng nhập...`);

  // Direct login
  currentUser = res.account;
  loginSuccess(currentUser);
}

function handleLogin() {
  const u = document.getElementById('login-username').value.trim().toLowerCase();
  const p = document.getElementById('login-password').value.trim();
  const errBanner = document.getElementById('login-error');

  if (!u || !p) {
    errBanner.innerText = 'Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu.';
    errBanner.style.display = 'block';
    return;
  }

  const accounts = store.getAccounts();
  const match = accounts.find(a => a.username.toLowerCase() === u && a.password === p);

  if (!match) {
    errBanner.innerText = 'Tên đăng nhập hoặc mật khẩu không chính xác.';
    errBanner.style.display = 'block';
    return;
  }

  errBanner.style.display = 'none';
  currentUser = match;
  loginSuccess(currentUser);
}

function loginSuccess(user) {
  // Update headers
  document.getElementById('display-user-name').innerText = user.displayName ? `${user.displayName} (@${user.username})` : `@${user.username}`;
  document.getElementById('display-user-role').innerText = user.isAdmin ? 'Quản trị viên' : 'Thành viên';
  updateHeaderDrops();

  // Show main screen
  document.getElementById('login-screen').classList.remove('active');
  document.getElementById('main-screen').classList.add('active');

  audio.startBgMusic();
  audio.playBell(528);
  resizeCanvas();
  renderCoreNeedList();
  renderChat();
  switchTab('tab-tree');
}

// TỰ ĐỘNG PHÁT NHẠC NỀN CẦU NGUYỆN KHI MỞ WEB
window.addEventListener('DOMContentLoaded', () => {
  audio.startBgMusic();
});
document.addEventListener('click', () => {
  if (!audio.hasStartedMusic && store.isSoundEnabled()) {
    audio.startBgMusic();
  }
}, { once: true });
document.addEventListener('touchstart', () => {
  if (!audio.hasStartedMusic && store.isSoundEnabled()) {
    audio.startBgMusic();
  }
}, { once: true });

// START CANVAS LOOP
drawTree();
resizeCanvas();
renderCoreNeedList();

// TỰ ĐỘNG XÓA NETLIFY BADGE / DRAWER INJECTION
const killNetlifyBadge = () => {
  const elements = document.querySelectorAll('iframe[src*="netlify"], iframe[id*="netlify"], [data-netlify-badge], netlify-drawer, #netlify-badge, .netlify-badge, [class*="netlify-feedback"], [id*="feedback-badge"]');
  elements.forEach(el => {
    try {
      el.remove();
    } catch {}
  });
};
setInterval(killNetlifyBadge, 400);
window.addEventListener('DOMContentLoaded', killNetlifyBadge);
window.addEventListener('load', killNetlifyBadge);
