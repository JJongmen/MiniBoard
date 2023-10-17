import React, { useState } from 'react';
import { Container, Typography, Paper, Box, TextField, Button } from '@mui/material';
import { createPost } from '../api/CreatePostApi';
import { useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { showSnackbar } from '../redux/snackbarSlice';

function PostWrite() {
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');

  let navigate = useNavigate();
  const dispatch = useDispatch();

  const handleWrite = async () => {
    try {
      const response = await createPost(title, content);
      console.log('게시글 작성 성공:', response);
      dispatch(showSnackbar({ message: '성공적으로 게시되었습니다!', severity: 'success' }));
      navigate(`/posts/${response.id}`);
    } catch (error) {
      console.error('게시글 작성 중 오류 발생:', error);
      dispatch(showSnackbar({ message: '게시글 게시에 실패했습니다.', severity: 'error' }));
    }
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