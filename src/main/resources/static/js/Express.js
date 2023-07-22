const express = require('express');
const app = express();

// Cấu hình định tuyến cho "/home"
app.get('/home', (req, res) => {
  // Xử lý yêu cầu đến "/home" ở đây, ví dụ: hiển thị trang chủ
  res.send('This is the home page.');
});

// Cấu hình định tuyến cho "/"
app.get('/', (req, res) => {
  // Xử lý yêu cầu đến "/" ở đây, ví dụ: chuyển hướng đến "/home"
  res.redirect('/home');
});

// Các đoạn mã xử lý khác ở đây...

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});