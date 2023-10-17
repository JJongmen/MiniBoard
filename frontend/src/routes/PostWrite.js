import React, { useState } from 'react';
import { Container, Typography, Paper, Box, TextField, Button } from '@mui/material';

function PostWrite() {
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');

  const handleWrite = () => {
    // TODO: API나 서버로 데이터 전송 코드 작성
    console.log('제목:', title);
    console.log('내용:', content);
  };

  return (
    <Container maxWidth="md" style={{ marginTop: '40px' }}>
      <Paper elevation={3} style={{ padding: '20px' }}>
        <Typography variant="h5" gutterBottom>
          글 작성하기
        </Typography>
        <TextField
          fullWidth
          label="제목"
          variant="outlined"
          style={{ marginBottom: '20px' }}
          value={title}
          onChange={(e) => setTitle(e.target.value)}
        />
        <TextField
          fullWidth
          label="내용"
          variant="outlined"
          multiline
          rows={10}
          value={content}
          onChange={(e) => setContent(e.target.value)}
        />
        <Box mt={4} display="flex" justifyContent="flex-end">
          <Button variant="contained" color="primary" onClick={handleWrite}>
            작성하기
          </Button>
        </Box>
      </Paper>
    </Container>
  );
}

export default PostWrite;