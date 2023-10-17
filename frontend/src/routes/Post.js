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
import { Link } from 'react-router-dom';

function Post() {
  let navigate = useNavigate();
  const dispatch = useDispatch();

  let { postId } = useParams();
  const [post, setPost] = useState({
    title: '',
    writerName: '',
    content: ''
  });

  useEffect(() => {
    const fetchData = async () => {
      try {
        const postDetail = await getPostDetail(postId);
        setPost({
          title: postDetail.title,
          writerName: postDetail.writerName,
          content: postDetail.content
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

  return (
    <Container maxWidth="md" style={{ marginTop: '40px' }}>
      <Paper elevation={3} style={{ padding: '20px' }}>
        <Typography variant="h5" gutterBottom>
          {post.title}
        </Typography>
        <Typography variant="subtitle1" gutterBottom>
          작성자: {post.writerName}
        </Typography>
        <Box mt={4}>
          <Typography variant="body1" paragraph>
            {post.content}
          </Typography>
        </Box>
        <Box mt={4} display="flex" justifyContent="flex-end">
          <Button component={Link} to={`/posts/${postId}/edit`}
            variant="contained" 
            color="primary"
            startIcon={<EditIcon />}
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