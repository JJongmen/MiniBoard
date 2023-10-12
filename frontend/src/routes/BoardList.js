import React from "react";
import { Table, Pagination } from "react-bootstrap";

let active = 1;
let items = [];
for (let number = 1; number <= 5; number++) {
  items.push(
    <Pagination.Item key={number} active={number === active}>
      {number}
    </Pagination.Item>
  );
}

const BoardList = () => {
    return (
    <div style={{ margin: '20px' }}>
    <Table striped bordered hover>
      <thead>
        <tr>
          <th>#</th>
          <th>제목</th>
          <th>작성자</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td>3</td>
          <td>가입인사드립니다!</td>
          <td>판다</td>
        </tr>
        <tr>
          <td>2</td>
          <td>냥</td>
          <td>고양이</td>
        </tr>
        <tr>
          <td>1</td>
          <td>안녕~~~</td>
          <td>쿼카</td>
        </tr>
      </tbody>
    </Table>

    <div style={{ display: 'flex', justifyContent: 'center', marginTop: '20px' }}>
      <Pagination>{items}</Pagination>
    </div>
    </div>
  );
};

export default BoardList;