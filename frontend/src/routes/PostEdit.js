import React, { useState, useEffect } from 'react';
import { Container, Typography, Paper, Box, TextField, Button } from '@mui/material';
import { useNavigate, useParams } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { showSnackbar } from '../redux/snackbarSlice';
import { getPostDetail } from '../api/GetPostDetailApi';
import { editPost } from '../api/EditPostApi';

function PostEdit() {
  const { postId } = useParams();
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  
  let navigate = useNavigate();
  const dispatch = useDispatch();

  useEffect(() => {
    const fetchPostDetail = async () => {
      try {
        const postDetail = await getPostDetail(postId);
        setTitle(postDetail.title);
        setContent(postDetail.content);
      } catch (error) {
        console.error('게시글 불러오기 오류:', error);
        dispatch(showSnackbar({ message: '게시글을 불러올 수 없습니다.', severity: 'error' }));
      }
    };
    fetchPostDetail();
  }, [postId, dispatch]);

  const handleEdit = async () => {
    try {
      await editPost(postId, title, content);
      dispatch(showSnackbar({ message: '게시글이 수정되었습니다!', severity: 'success' }));
      navigate(`/posts/${postId}`);
    } catch (error) {
      console.error('게시글 수정 오류:', error);
      dispatch(showSnackbar({ message: '게시글 수정에 실패했습니다.', severity: 'error' }));
    }
  };

  return (
    <Container maxWidth="md" style={{ marginTop: '40px' }}>
      <Paper elevation={3} style={{ padding: '20px' }}>
        <Typography variant="h5" gutterBottom>
          글 수정하기
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
          <Button variant="contained" color="primary" onClick={handleEdit}>
            수정하기
          </Button>
        </Box>
      </Paper>
    </Container>
  );
}

export default PostEdit;