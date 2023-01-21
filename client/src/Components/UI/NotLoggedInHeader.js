import React from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';

const StyledNotLoggedInHeader = styled.ul`
  display: flex;
  flex-direction: row;
  align-items: center;

  li {
    display: inline-flex;
    padding: 8px 10px;
    border-radius: 3px;
    margin: 0 2px;
  }
`;

const NotLoggedInHeader = () => {
  return (
    <StyledNotLoggedInHeader>
      <li className="btn-style2">
        <Link to="/login">Log in</Link>
      </li>
      <li className="btn-style1">
        <Link to="/signup">Sign up</Link>
      </li>
    </StyledNotLoggedInHeader>
  );
};

export default NotLoggedInHeader;
