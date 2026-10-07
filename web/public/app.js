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
  { id: 'anxiety', label: 'Bình An Trong Lo Âu', desc: 'Khi tâm trí ngổn ngang lo toan về cơm áo gạo tiền, sức khỏe hay ngày mai.', scripture: 'Chớ lo phiền chi hết, song trong mọi sự hãy dùng lời cầu nguyện... mà bày tỏ sự cầu xin lên Đức Chúa Trời.', ref: 'Phi-líp 4:6-7', x: 0.22, y: 0.33 },
  { id: 'loneliness', label: 'Hiện Diện Khi Cô Đơn', desc: 'Cảm giác lạc lõng, trống vắng không ai thấu hiểu nỗi lòng riêng.', scripture: 'Ta sẽ chẳng lìa ngươi, chẳng bỏ ngươi đâu.', ref: 'Hê-bơ-rơ 13:5', x: 0.78, y: 0.33 },
  { id: 'healing', label: 'Chữa Lành Tổn Thương', desc: 'Vết thương lòng do bị phản bội, ruồng bỏ, lời nói cay độc xâu xé.', scripture: 'Đức Giê-hô-va chữa lành những người có lòng đau thương và băng bó các vết tích của họ.', ref: 'Thi-thiên 147:3', x: 0.16, y: 0.44 },
  { id: 'forgiveness', label: 'Giải Phóng Mặc Cảm Tội Lỗi', desc: 'Dằn vặt vì những sai lầm trong quá khứ, tự kết án bản thân.', scripture: 'Còn nếu chúng ta xưng tội mình, thì Ngài là thành tín công bình để tha tội cho chúng ta.', ref: '1 Giăng 1:9', x: 0.84, y: 0.44 },
  { id: 'direction', label: 'Sự Soi Dẫn Khi Lạc Lối', desc: 'Đứng trước ngã rẽ cuộc đời không biết đi về đâu, thiếu sự khôn ngoan.', scripture: 'Lời Chúa là ngọn đèn cho chân tôi, ánh sáng cho đường lối tôi.', ref: 'Thi-thiên 119:105', x: 0.26, y: 0.25 },
  { id: 'comfort', label: 'An Ủi Khi Tang Chế & Mất Mát', desc: 'Nỗi đau chia lìa, mất đi người thân yêu hoặc ước mơ sụp đổ.', scripture: 'Phước cho những kẻ than khóc, vì sẽ được an ủi!', ref: 'Ma-thi-ơ 5:4', x: 0.74, y: 0.25 },
  { id: 'strength', label: 'Sức Mạnh Khi Kiệt Sức', desc: 'Gánh nặng công việc, chăm sóc gia đình đè nặng không còn sức lực.', scripture: 'Những ai trông đợi Đức Giê-hô-va chắc chắn được sức mới; cất cánh bay cao như chim ưng.', ref: 'Ê-sai 40:31', x: 0.50, y: 0.15 },
  { id: 'provision', label: 'Tiếp Trợ Nhu Cầu Vật Chất', desc: 'Bế tắc tài chính, nợ nần, thiếu thốn chi phí sinh hoạt hàng ngày.', scripture: 'Đức Chúa Trời tôi sẽ làm cho đầy đủ mọi sự cần dùng của anh em theo sự giàu có của Ngài.', ref: 'Phi-líp 4:19', x: 0.38, y: 0.20 },
  { id: 'reconciliation', label: 'Hòa Giải Mối Quan Hệ', desc: 'Xung đột vợ chồng, cha mẹ con cái bất hòa, bạn bè hiểu lầm xa cách.', scripture: 'Hãy hết sức mình tìm kiếm sự hòa thuận với mọi người.', ref: 'Rô-ma 12:18', x: 0.62, y: 0.20 },
  { id: 'protection', label: 'Bảo Vệ Khỏi Ác Độc', desc: 'Sự sợ hãi trước những kẻ quấy nhiễu, nguy hiểm rình rập, điều dữ.', scripture: 'Đức Giê-hô-va sẽ gìn giữ ngươi khỏi mọi tai họa; Ngài sẽ bảo tồn linh hồn ngươi.', ref: 'Thi-thiên 121:7', x: 0.44, y: 0.36 },
  { id: 'identity', label: 'Nhận Biết Giá Trị Bản Thân', desc: 'Tự ti, cảm thấy mình vô dụng, kém cỏi so với người khác.', scripture: 'Ngài đã chọn chúng ta trước khi sáng thế... Ngài gọi ngươi bằng tên ngươi; ngươi thuộc về Ta.', ref: 'Ê-phê-sô 1:4 / Ê-sai 43:1', x: 0.56, y: 0.36 },
  { id: 'hope', label: 'Hy Vọng Cho Tương Lai', desc: 'Tuyệt vọng, thấy tương lai tăm tối mù mịt không lối thoát.', scripture: 'Vì Ta biết ý tưởng Ta nghĩ đối cùng các ngươi... là ý tưởng bình an, chẳng phải tai họa, để cho các ngươi được sự trông cậy trong lúc cuối cùng.', ref: 'Giê-rê-mi 29:11', x: 0.50, y: 0.37 },
  { id: 'selfcontrol', label: 'Chiến Thắng Cám Dỗ', desc: 'Bị trói buộc bởi thói quen xấu, nghiện ngập, dục vọng thể xác.', scripture: 'Những sự cám dỗ đến cho anh em... Đức Chúa Trời là thành tín, chẳng hề để anh em bị cám dỗ quá sức mình đâu.', ref: '1 Cô-rinh-tô 10:13', x: 0.50, y: 0.46 }
];

// 2. AUDIO SYNTHESIZER (528Hz & Water Sounds)
class SanctuaryAudio {
  constructor() {
    this.ctx = null;
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

  playBell(freq = 528) {
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

  getUserData(uname) {
    try {
      const data = localStorage.getItem(this.prefix + 'user_' + uname);
      if (data) return JSON.parse(data);
    } catch {}
    return {
      waterDrops: 120,
      amenFruits: [],
      amenLeaves: [],
      isNegative: false,
      journals: [],
      chats: [
        { role: 'bot', text: 'Bình an cho bạn. Hôm nay tâm hồn bạn thế nào? Hãy chia sẻ những gánh nặng cùng Chúa nhé.' }
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

  // 5. ROOTS
  const rootColor = '#8D532B';
  const roots = [
    { start: [w * 0.48, soilY], end: [w * 0.22, h * 0.94], bend: w * 0.32 },
    { start: [w * 0.50, soilY], end: [w * 0.48, h * 0.96], bend: w * 0.49 },
    { start: [w * 0.52, soilY], end: [w * 0.78, h * 0.94], bend: w * 0.68 }
  ];

  roots.forEach((r, idx) => {
    ctx.beginPath();
    ctx.strokeStyle = rootColor;
    ctx.lineWidth = 5;
    ctx.lineCap = 'round';
    ctx.moveTo(r.start[0], r.start[1]);
    ctx.quadraticCurveTo(r.bend, (soilY + r.end[1]) / 2, r.end[0], r.end[1]);
    ctx.stroke();

    // Golden divine glow on roots when water dissolves rocks
    if (isWateringAnim && wateringProgress > 0.4) {
      ctx.beginPath();
      ctx.strokeStyle = '#FEF08A';
      ctx.lineWidth = 2.5;
      ctx.globalAlpha = (wateringProgress - 0.4) * 1.4;
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
      // Rock body
      ctx.fillStyle = '#64748B';
      ctx.beginPath();
      ctx.arc(rock.x, rock.y, rock.r, 0, Math.PI * 2);
      ctx.fill();

      // Rock highlights
      ctx.fillStyle = '#94A3B8';
      ctx.beginPath();
      ctx.arc(rock.x - rock.r * 0.3, rock.y - rock.r * 0.3, rock.r * 0.4, 0, Math.PI * 2);
      ctx.fill();

      // Glowing crack veins when dissolving
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

  // 8. TREE TRUNK
  const trunkGrad = ctx.createLinearGradient(w * 0.4, 0, w * 0.6, 0);
  trunkGrad.addColorStop(0, '#452715');
  trunkGrad.addColorStop(0.5, '#784423');
  trunkGrad.addColorStop(1, '#452715');

  ctx.fillStyle = trunkGrad;
  ctx.beginPath();
  ctx.moveTo(w * 0.42, soilY);
  ctx.bezierCurveTo(w * 0.44, h * 0.60, w * 0.45, h * 0.40, w * 0.38, h * 0.28);
  ctx.bezierCurveTo(w * 0.46, h * 0.35, w * 0.50, h * 0.42, w * 0.50, h * 0.48);
  ctx.bezierCurveTo(w * 0.54, h * 0.35, w * 0.58, h * 0.30, w * 0.62, h * 0.28);
  ctx.bezierCurveTo(w * 0.55, h * 0.40, w * 0.56, h * 0.60, w * 0.58, soilY);
  ctx.closePath();
  ctx.fill();

  // 9. BRAMBLES & TARES (Burn during Holy Fire)
  const weedAlpha = uData.isNegative ? (isHolyFireAnim ? Math.max(0, 1.0 - fireProgress) : 1.0) : 0;
  if (weedAlpha > 0.02) {
    ctx.globalAlpha = weedAlpha;
    // Brambles around trunk
    ctx.strokeStyle = '#991B1B';
    ctx.lineWidth = 3.5;
    ctx.beginPath();
    ctx.arc(w * 0.5, soilY - 10, 32, Math.PI, 0);
    ctx.stroke();

    // Tall Tares (Cỏ lùng)
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

      // Tare head (dark seeds or flaming torch)
      ctx.fillStyle = isBurning ? '#FEF08A' : '#451A03';
      ctx.beginPath();
      ctx.ellipse(t.x + t.lean, soilY - t.h - 5, 5, 10, 0, 0, Math.PI * 2);
      ctx.fill();

      // Fire flame licking up the tare
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

  canopies.forEach((c, idx) => {
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

    // Glowing halo
    ctx.fillStyle = isAmen ? 'rgba(254, 240, 138, 0.45)' : 'rgba(255, 255, 255, 0.15)';
    ctx.beginPath();
    ctx.arc(fx, fy, 16, 0, Math.PI * 2);
    ctx.fill();

    // Fruit body
    ctx.fillStyle = isAmen ? '#F59E0B' : f.color;
    ctx.beginPath();
    ctx.arc(fx, fy, 11, 0, Math.PI * 2);
    ctx.fill();

    // Little fruit shine
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

    ctx.fillStyle = isAmen ? '#6EE7B7' : '#059669';
    ctx.beginPath();
    ctx.ellipse(lx, ly, 4.5, 9, Math.sin(tick + l.x * 10) * 0.3, 0, Math.PI * 2);
    ctx.fill();
  });

  // 13. ANGEL WATERING ANIMATION
  if (isWateringAnim) {
    const angelX = w * 0.50;
    const angelY = h * 0.12;

    // Divine halo
    ctx.fillStyle = 'rgba(253, 224, 71, 0.35)';
    ctx.beginPath();
    ctx.arc(angelX, angelY, 40, 0, Math.PI * 2);
    ctx.fill();

    // Angel Wings
    ctx.fillStyle = '#FFF';
    ctx.beginPath();
    ctx.ellipse(angelX - 25, angelY + 5, 20, 10, -0.4, 0, Math.PI * 2);
    ctx.ellipse(angelX + 25, angelY + 5, 20, 10, 0.4, 0, Math.PI * 2);
    ctx.fill();

    // Angel Head & Robe
    ctx.fillStyle = '#FEF08A';
    ctx.beginPath();
    ctx.arc(angelX, angelY - 8, 9, 0, Math.PI * 2);
    ctx.fill();
    ctx.fillStyle = '#FFF';
    ctx.beginPath();
    ctx.moveTo(angelX, angelY);
    ctx.lineTo(angelX - 14, angelY + 28);
    ctx.lineTo(angelX + 14, angelY + 28);
    ctx.closePath();
    ctx.fill();

    // Jar tilted
    ctx.fillStyle = '#F59E0B';
    ctx.beginPath();
    ctx.ellipse(angelX + 14, angelY + 12, 7, 10, -0.6, 0, Math.PI * 2);
    ctx.fill();

    // Water Torrent Pouring Down
    const waterGrad = ctx.createLinearGradient(0, angelY + 18, 0, soilY + 50);
    waterGrad.addColorStop(0, '#67E8F9');
    waterGrad.addColorStop(0.5, '#06B6D4');
    waterGrad.addColorStop(1, '#059669');

    ctx.fillStyle = waterGrad;
    ctx.beginPath();
    ctx.moveTo(angelX + 16, angelY + 18);
    ctx.lineTo(angelX - 35, soilY + 30);
    ctx.lineTo(angelX + 55, soilY + 30);
    ctx.closePath();
    ctx.globalAlpha = 0.75;
    ctx.fill();
    ctx.globalAlpha = 1.0;
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
  document.getElementById('fruit-modal').style.display = 'flex';
}

function openLeafModal(leaf) {
  currentSelectedLeaf = leaf;
  audio.playBell(528);
  document.getElementById('leaf-modal-title').innerText = leaf.label;
  document.getElementById('leaf-modal-desc').innerText = leaf.desc;
  document.getElementById('leaf-modal-scripture').innerText = `"${leaf.scripture}"`;
  document.getElementById('leaf-modal-ref').innerText = leaf.ref;
  document.getElementById('leaf-modal').style.display = 'flex';
}

function openRootModal() {
  audio.playBell(528);
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

// TRIGGER WATERING ANIMATION
function triggerWatering() {
  audio.playBell(528);
  audio.playWaterDrop();
  isWateringAnim = true;
  wateringProgress = 0;
  rockDissolve = 0;
  showBanner('Thiên Sứ giáng lâm cầm bình ngọc đổ Nước Hằng Sống...');

  const startTime = Date.now();
  const duration = 4000;

  function step() {
    const elapsed = Date.now() - startTime;
    wateringProgress = Math.min(1.0, elapsed / duration);
    rockDissolve = wateringProgress; // rocks dissolve progressively

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
      showBanner('Dòng nước đã làm tan biến toàn bộ sỏi đá cản rễ!');
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

// QUICK ACTIONS
document.getElementById('btn-toggle-weeds').addEventListener('click', () => {
  if (!currentUser) return;
  const uData = store.getUserData(currentUser.username);
  uData.isNegative = !uData.isNegative;
  rockDissolve = uData.isNegative ? 0 : 1;
  store.saveUserData(currentUser.username, uData);
  showBanner(uData.isNegative ? 'Đã xuất hiện sỏi đá, cỏ lùng và bụi gai!' : 'Mảnh đất lòng đã được giải phóng!');
});

document.getElementById('btn-add-water').addEventListener('click', () => {
  if (!currentUser) return;
  const uData = store.getUserData(currentUser.username);
  uData.waterDrops += 100;
  store.saveUserData(currentUser.username, uData);
  updateHeaderDrops();
  audio.playWaterDrop();
  showBanner('Đã nhận thêm 100 Giọt Nước Sự Sống!');
});

document.getElementById('btn-test-watering').addEventListener('click', triggerWatering);
document.getElementById('btn-test-fire').addEventListener('click', triggerHolyFire);

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
  setTimeout(() => { b.style.display = 'none'; }, 3500);
}

function updateHeaderDrops() {
  if (!currentUser) return;
  const uData = store.getUserData(currentUser.username);
  document.getElementById('header-drops-count').innerText = uData.waterDrops;
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
      <div>
        <div class="need-item-title">${n.label}</div>
        <div class="need-item-sub">${n.desc}</div>
      </div>
      <span>→</span>
    `;
    item.addEventListener('click', () => openLeafModal(n));
    container.appendChild(item);
  });
}

// 6. TAB 3: JOURNAL
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
  uData.waterDrops += 1;
  if (!uData.journals) uData.journals = [];
  uData.journals.unshift({
    title,
    content,
    mood: selectedMood,
    date: new Date().toLocaleDateString('vi-VN', { hour: '2-digit', minute: '2-digit', day: '2-digit', month: '2-digit' })
  });
  store.saveUserData(currentUser.username, uData);
  document.getElementById('journal-title').value = '';
  document.getElementById('journal-content').value = '';
  updateHeaderDrops();
  audio.playBell(528);
  renderJournals();
  alert('Đã lưu vào nhật ký (+1 Giọt Nước)!');
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

// 7. TAB 4: CHAT
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
  uData.chats.push({ role: 'user', text });
  renderChat();

  // Temporary thinking indicator
  const botMsgObj = { role: 'bot', text: '🕊️ Đang suy gẫm và lắng nghe tiếng Chúa...' };
  uData.chats.push(botMsgObj);
  renderChat();

  try {
    const customKey = store.getGeminiKey() || '';
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
      botMsgObj.text = data.text;
    } else {
      botMsgObj.text = generateBiblicalResponse(text);
    }
  } catch (err) {
    botMsgObj.text = generateBiblicalResponse(text);
  }

  store.saveUserData(currentUser.username, uData);
  renderChat();
  audio.playBell(528);
}

function generateBiblicalResponse(query) {
  const q = query.toLowerCase();
  for (let need of CORE_NEEDS) {
    if (q.includes('lo') || q.includes('sợ') || q.includes('buồn') || q.includes('cô đơn') || q.includes('đau')) {
      return `Chúa hiểu rõ nỗi lòng bạn lúc này. Lời Ngài hứa cùng bạn:\n"${need.scripture}" (${need.ref})\nHãy dâng gánh nặng này lên nơi chân Ngài.`;
    }
  }
  return `Nguyện xin sự bình an của Chúa gìn giữ tâm hồn bạn. "Hãy trao mọi điều lo lắng mình cho Ngài, vì Ngài hay săn sóc anh em." (1 Phi-e-rơ 5:7)`;
}

// 8. TAB 5: SETTINGS
function renderSettings() {
  if (!currentUser) return;
  document.getElementById('settings-username').innerText = `@${currentUser.username}`;
  document.getElementById('settings-display-name').value = currentUser.displayName || currentUser.username;
  document.getElementById('settings-gemini-key').value = store.getGeminiKey();

  const adminCard = document.getElementById('admin-management-card');
  if (currentUser.isAdmin) {
    adminCard.style.display = 'block';
    renderAdminMemberList();
  } else {
    adminCard.style.display = 'none';
  }
}

function renderAdminMemberList() {
  const list = document.getElementById('admin-member-list');
  list.innerHTML = '';
  const accounts = store.getAccounts();
  accounts.forEach(acc => {
    const row = document.createElement('div');
    row.className = 'member-row';
    row.innerHTML = `
      <span>@${acc.username} (${acc.displayName})</span>
      <span style="color:#6EE7B7;">${acc.isAdmin ? '👑 Admin' : '👤 Thành viên'}</span>
    `;
    list.appendChild(row);
  });
}

document.getElementById('btn-add-member').addEventListener('click', () => {
  const val = document.getElementById('new-member-username').value.trim();
  if (!val) return;
  const ok = store.addAccount(val);
  if (ok) {
    document.getElementById('new-member-username').value = '';
    renderAdminMemberList();
    alert(`Đã thêm thành viên @${val.toLowerCase()} (Mật khẩu: 12345678)`);
  } else {
    alert('Tài khoản đã tồn tại!');
  }
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
    alert('Đã sao chép toàn bộ dữ liệu JSON vào bộ nhớ đệm!');
  }).catch(() => {
    alert('Vui lòng cấp quyền sao chép.');
  });
});

// LOGOUT
function logout() {
  currentUser = null;
  document.getElementById('main-screen').classList.remove('active');
  document.getElementById('login-screen').classList.add('active');
  document.getElementById('login-password').value = '';
}

document.getElementById('btn-quick-logout').addEventListener('click', logout);
document.getElementById('btn-settings-logout').addEventListener('click', logout);

// 9. LOGIN LOGIC
document.getElementById('toggle-pwd-btn').addEventListener('click', () => {
  const pwdInput = document.getElementById('login-password');
  pwdInput.type = pwdInput.type === 'password' ? 'text' : 'password';
});

document.getElementById('btn-login-submit').addEventListener('click', handleLogin);
document.getElementById('login-password').addEventListener('keydown', (e) => {
  if (e.key === 'Enter') handleLogin();
});

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

  // Update headers
  document.getElementById('display-user-name').innerText = `@${match.username}`;
  document.getElementById('display-user-role').innerText = match.isAdmin ? 'Quản trị viên' : 'Thành viên';
  updateHeaderDrops();

  // Show main screen
  document.getElementById('login-screen').classList.remove('active');
  document.getElementById('main-screen').classList.add('active');

  audio.playBell(528);
  resizeCanvas();
  renderCoreNeedList();
  renderChat();
  switchTab('tab-tree');
}

// START CANVAS LOOP
drawTree();
resizeCanvas();
renderCoreNeedList();
