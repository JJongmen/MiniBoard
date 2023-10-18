import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { Container, Typography, Paper, Box, Button } from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import { getPostDetail } from '../api/GetPostDetailApi';
import { deletePost } from '../api/DeletePostApi';
import { useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { showSnackbar } from '../redux/snackbarSlice';
import { useAuth } from '../auth/AuthContext';
import { formatDate } from '../utils/formatDateUtil';

function Post() {
  let navigate = useNavigate();
  const dispatch = useDispatch();
  const { userName } = useAuth()

  let { postId } = useParams();
  const [post, setPost] = useState({
    title: '',
    writerName: '',
    content: '',
    createdAt: ''
});

  useEffect(() => {
    const fetchData = async () => {
      try {
          const postDetail = await getPostDetail(postId);
          setPost({
            title: postDetail.title,
            writerName: postDetail.writerName,
            content: postDetail.content,
            createdAt: postDetail.createdAt
          });
      } catch (error) {
          console.error("게시글을 불러오는데 실패했습니다.", error);
      }
  };

    fetchData();
  }, [postId]);

  const handleDelete = async () => {
    try {
      await deletePost(postId);
      dispatch(showSnackbar({ message: '게시글이 삭제되었습니다.', severity: 'success' }));
      navigate('/');
    } catch (error) {
      console.error("게시글 삭제에 실패했습니다.", error);
      if (error.code === 'NOT_MATCH_MEMBER') {
        dispatch(showSnackbar({ message: '자신이 작성하지 않은 글은 삭제할 수 없습니다.', severity: 'error' }));
      } else {
        dispatch(showSnackbar({ message: '게시글 삭제에 실패했습니다.', severity: 'error' }));
      }
    }
  };

  const handleEdit = () => {
        if (post.writerName !== userName) {
            dispatch(showSnackbar({ message: '자신이 작성하지 않은 글은 수정할 수 없습니다.', severity: 'error' }));
            return;
        }
        navigate(`/posts/${postId}/edit`);
  };

  return (
    <Container maxWidth="md" style={{ marginTop: '40px' }}>
      <Paper elevation={3} style={{ padding: '20px' }}>
        <Typography variant="h5" gutterBottom>
          {post.title}
        </Typography>
        <Typography variant="subtitle1" gutterBottom>
          작성일: {post.createdAt && formatDate(post.createdAt)}
        </Typography>
        <Typography variant="subtitle2" gutterBottom>
          작성자: {post.writerName}
        </Typography>
        <Box mt={4}>
          <Typography variant="body1" paragraph>
            {post.content}
          </Typography>
        </Box>
        <Box mt={4} display="flex" justifyContent="flex-end">
          <Button
            variant="contained" 
            color="primary"
            startIcon={<EditIcon />}
            onClick={handleEdit}
            style={{ marginRight: '10px' }}
          >
            수정
          </Button>
          <Button 
            variant="contained" 
            color="secondary"
            startIcon={<DeleteIcon />}
            onClick={handleDelete}
          >
            삭제
          </Button>
        </Box>
      </Paper>
    </Container>
  );
}

export default Post;