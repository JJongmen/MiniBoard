import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import { Link as RouterLink, useNavigate } from 'react-router-dom';

export default function Header() {

  let navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem('access_token');
    localStorage.removeItem('user_name');
    navigate('/');
  };
  
  // 토큰의 존재 여부를 통해 로그인 상태 확인
  const isLoggedIn = !!localStorage.getItem('access_token');
  const userName = localStorage.getItem('user_name'); 

  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static">
        <Toolbar>
          <IconButton
            size="large"
            edge="start"
            color="inherit"
            aria-label="menu"
            sx={{ mr: 2 }}
          >
            <MenuIcon />
          </IconButton>
          <Typography 
            variant="h6" 
            component={RouterLink} 
            to="/" 
            sx={{ flexGrow: 1, textDecoration: 'none', color: 'inherit' }}
          >
            게시판
          </Typography>
          {userName && <Typography variant="h6">{userName}</Typography>}
          {
            isLoggedIn ? (
              // 로그인 상태일 때 로그아웃 버튼 표시
              <Button color="inherit" onClick={handleLogout}>
                로그아웃
              </Button>
            ) : (
              // 로그아웃 상태일 때 로그인 버튼 표시
              <Button component={RouterLink} to="/sign-in" color="inherit">
                로그인
              </Button>
            )
          }
        </Toolbar>
      </AppBar>
    </Box>
  );
}