import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import { Link, useNavigate } from 'react-router-dom';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import { signIn } from '../api/SignInApi';
import { useDispatch } from 'react-redux';
import { showSnackbar } from '../redux/snackbarSlice';
import { useAuth } from '../auth/AuthContext';

const defaultTheme = createTheme();

export default function SignIn() {
  let navigate = useNavigate();
  const dispatch = useDispatch();

  const { login } = useAuth();

  const handleSubmit = async (event) => {
    event.preventDefault();
    const email = event.currentTarget.email.value;
    const password = event.currentTarget.password.value;

    try {
      // API 호출하여 로그인 시도
      const response = await signIn(email, password);
      
      // 성공적인 응답에 따른 처리
      login(response.data.token, response.data.name);
      console.log('Login successful:', response.data);
      dispatch(showSnackbar({ message: '로그인에 성공했습니다!', severity: 'success' }));
      navigate('/');
    } catch (error) {
      // 로그인 실패 시 처리
      console.error('Login failed:', error);
      dispatch(showSnackbar({ message: '로그인에 실패했습니다.', severity: 'error' }));
    }
  };

  return (
    <ThemeProvider theme={defaultTheme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            로그인
          </Typography>
          <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
            <TextField
              margin="normal"
              required
              fullWidth
              id="email"
              label="이메일 주소"
              name="email"
              autoComplete="email"
              autoFocus
            />
            <TextField
              margin="normal"
              required
              fullWidth
              name="password"
              label="비밀번호"
              type="password"
              id="password"
              autoComplete="current-password"
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              로그인
            </Button>
            <Grid container>
              <Grid item>
                <Link to="/sign-up" variant="body2">
                  {"가입하기"}
                </Link>
              </Grid>
            </Grid>
          </Box>
        </Box>
      </Container>
    </ThemeProvider>
  );
}