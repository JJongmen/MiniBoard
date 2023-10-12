import React from "react";
import { Col, Button, Form, Row, Container } from "react-bootstrap";

const SignIn = () => {
    return (
      <Container className="d-flex justify-content-center align-items-center" style={{ minHeight: "60vh" }}>
        <Col xs={12} sm={10} md={8} lg={6}>
          <Form>
            <Form.Group className="mb-3" controlId="formGroupEmail">
              <Form.Label>Email address</Form.Label>
              <Form.Control type="email" placeholder="Enter email" />
            </Form.Group>
            <Form.Group className="mb-3" controlId="formGroupPassword">
              <Form.Label>Password</Form.Label>
              <Form.Control type="password" placeholder="Password" />
            </Form.Group>
            <Form.Group as={Row} className="mb-3">
              <Col sm={{ span: 10, offset: 1 }}>
                <Button type="submit" block>
                  Sign in
                </Button>
              </Col>
            </Form.Group>
          </Form>
        </Col>
      </Container>
    );
  };

export default SignIn;