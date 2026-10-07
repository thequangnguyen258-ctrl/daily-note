const http = require('http');
const https = require('https');
const fs = require('fs');
const path = require('path');

const PORT = process.env.PORT_APP || 3000;
const PUBLIC_DIR = path.join(__dirname, 'public');

const MIME_TYPES = {
  '.html': 'text/html; charset=utf-8',
  '.js': 'application/javascript; charset=utf-8',
  '.css': 'text/css; charset=utf-8',
  '.json': 'application/json; charset=utf-8',
  '.png': 'image/png',
  '.jpg': 'image/jpeg',
  '.svg': 'image/svg+xml',
  '.ico': 'image/x-icon',
  '.wav': 'audio/wav',
  '.mp3': 'audio/mpeg'
};

const server = http.createServer((req, res) => {
  // Enable CORS
  res.setHeader('Access-Control-Allow-Origin', '*');
  res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS');
  res.setHeader('Access-Control-Allow-Headers', 'Content-Type, Authorization');

  if (req.method === 'OPTIONS') {
    res.writeHead(204);
    res.end();
    return;
  }

  // Health check endpoint
  if (req.url === '/health' || req.url === '/api/health') {
    res.writeHead(200, { 'Content-Type': 'application/json' });
    res.end(JSON.stringify({ status: 'ok', time: new Date().toISOString() }));
    return;
  }

  // GEMINI API PROXY ENDPOINT
  if (req.url === '/api/gemini' && req.method === 'POST') {
    let body = '';
    req.on('data', chunk => { body += chunk; });
    req.on('end', () => {
      try {
        const payload = JSON.parse(body || '{}');
        const userMsg = payload.message || '';
        const history = payload.history || [];
        const customKey = payload.apiKey || '';

        const effectiveKey = (customKey.trim() || process.env.GEMINI_API_KEY || '').trim();

        if (!effectiveKey) {
          res.writeHead(400, { 'Content-Type': 'application/json' });
          res.end(JSON.stringify({ error: 'Chưa có Gemini API Key' }));
          return;
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

        const geminiUrl = `https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=${effectiveKey}`;

        const geminiReq = https.request(geminiUrl, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Content-Length': Buffer.byteLength(geminiRequestBody)
          }
        }, (geminiRes) => {
          let gData = '';
          geminiRes.on('data', c => { gData += c; });
          geminiRes.on('end', () => {
            try {
              const parsed = JSON.parse(gData);
              const replyText = parsed?.candidates?.[0]?.content?.parts?.[0]?.text;
              if (replyText) {
                res.writeHead(200, { 'Content-Type': 'application/json' });
                res.end(JSON.stringify({ text: replyText, isFromGemini: true }));
              } else {
                res.writeHead(200, { 'Content-Type': 'application/json' });
                res.end(JSON.stringify({
                  text: null,
                  raw: parsed,
                  error: parsed?.error?.message || 'Không nhận được câu trả lời từ Gemini'
                }));
              }
            } catch (err) {
              res.writeHead(500, { 'Content-Type': 'application/json' });
              res.end(JSON.stringify({ error: 'Lỗi phân tích phản hồi Gemini' }));
            }
          });
        });

        geminiReq.on('error', (e) => {
          res.writeHead(500, { 'Content-Type': 'application/json' });
          res.end(JSON.stringify({ error: e.message }));
        });

        geminiReq.write(geminiRequestBody);
        geminiReq.end();

      } catch (err) {
        res.writeHead(400, { 'Content-Type': 'application/json' });
        res.end(JSON.stringify({ error: 'Dữ liệu yêu cầu không hợp lệ' }));
      }
    });
    return;
  }

  // Parse static files
  let reqPath = req.url.split('?')[0];
  if (reqPath === '/' || reqPath === '') {
    reqPath = '/index.html';
  }

  let filePath = path.join(PUBLIC_DIR, reqPath);
  if (!filePath.startsWith(PUBLIC_DIR)) {
    res.writeHead(403);
    res.end('Forbidden');
    return;
  }

  fs.stat(filePath, (err, stats) => {
    if (err || !stats.isFile()) {
      filePath = path.join(PUBLIC_DIR, 'index.html');
    }

    const ext = path.extname(filePath).toLowerCase();
    const contentType = MIME_TYPES[ext] || 'application/octet-stream';

    fs.readFile(filePath, (readErr, content) => {
      if (readErr) {
        res.writeHead(500, { 'Content-Type': 'text/plain' });
        res.end('Server Error');
        return;
      }
      res.writeHead(200, { 'Content-Type': contentType });
      res.end(content);
    });
  });
});

server.listen(PORT, '0.0.0.0', () => {
  console.log(`[Web Server] Cây Sự Sống Web App listening on port ${PORT}`);
});
