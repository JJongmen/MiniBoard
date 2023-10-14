import React from 'react';
import { useParams } from 'react-router-dom';
import { Container, Typography, Paper, Box } from '@mui/material';

function Post() {
  let { postId } = useParams();

  // postId를 사용하여 API 호출 및 데이터 로드
  // 예시로 임시 데이터를 사용합니다.
  const post = {
    title: '샘플 게시글 제목',
    author: '작성자 이름',
    content: `여기에 게시글의 내용이 들어갑니다.`,
  };

  return (
    <Container maxWidth="md" style={{ marginTop: '40px' }}>
      <Paper elevation={3} style={{ padding: '20px' }}>
        <Typography variant="h5" gutterBottom>
          {post.title}
        </Typography>
        <Typography variant="subtitle1" gutterBottom>
          작성자: {post.author}
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