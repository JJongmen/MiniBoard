import React from 'react';
import { useParams } from 'react-router-dom';
import { Container, Typography, Paper, Box } from '@mui/material';
import { useState } from 'react';
import { useEffect } from 'react';
import { getPostDetail } from '../api/GetPostDetailApi';

function Post() {
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
      </Paper>
    </Container>
  );
}

export default Post;