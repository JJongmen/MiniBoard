import * as React from 'react';
import { styled } from '@mui/material/styles';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell, { tableCellClasses } from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { Pagination } from '@mui/material';
import { Link } from 'react-router-dom';

const StyledTableCell = styled(TableCell)(({ theme }) => ({
  [`&.${tableCellClasses.head}`]: {
    backgroundColor: '#c0c0c0',
    color: theme.palette.common.black,
  },
  [`&.${tableCellClasses.body}`]: {
    fontSize: 14,
  },
}));

const StyledTableRow = styled(TableRow)(({ theme }) => ({
  '&:nth-of-type(odd)': {
    backgroundColor: theme.palette.action.hover,
  },
  // hide last border
  '&:last-child td, &:last-child th': {
    border: 0,
  },
}));

function createData(id, title, name) {
  return { id, title, name };
}

const rows = [
  createData(5, '멍멍', '강아지'),
  createData(4, '꿀꿀', '돼지'),
  createData(3, '냐옹', '고양이'),
  createData(2, '파아안다', '판다'),
  createData(1, '쿼어어어카', '쿼카'),
];

export default function Board() {
    return (
      <div style={{ margin: '20px' }}>
        <TableContainer component={Paper}>
          <Table sx={{ minWidth: 700, display: 'flex', flexDirection: 'column' }} aria-label="customized table">
            <TableHead>
              <TableRow sx={{ display: 'flex' }}>
                <StyledTableCell sx={{ flex: 1 }}>#</StyledTableCell>
                <StyledTableCell sx={{ flex: 10 }} align="left">
                  제목
                </StyledTableCell>
                <StyledTableCell sx={{ flex: 3 }} align="left">
                  작성자
                </StyledTableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {rows.map((row) => (
                <StyledTableRow component={Link} to={`/posts/${row.id}`} key={row.id} sx={{ display: 'flex' }}>
                  <StyledTableCell sx={{ flex: 1 }} component="th" scope="row">
                    {row.id}
                  </StyledTableCell>
                  <StyledTableCell sx={{ flex: 10 }} align="left">{row.title}</StyledTableCell>
                  <StyledTableCell sx={{ flex: 3 }} align="left">{row.name}</StyledTableCell>
                </StyledTableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
        <div style={{ display: 'flex', justifyContent: 'center', marginTop: '20px' }}>
            <Pagination count={10} color="primary" />
        </div>
      </div>
    );
  }
  