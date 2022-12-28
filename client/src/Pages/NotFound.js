import React, { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import styled from 'styled-components';

const StyledNotFound = styled.div`
  width: 100%;
`;

const NotFound = () => {
  const navigate = useNavigate();
  const location = useLocation().pathname;

  useEffect(() => {
    if (location !== '/not-found-page') {
      navigate('/not-found-page');
      window.location.reload();
    }
  }, []);
  return (
    <StyledNotFound>
      <h2>Page not found</h2>
    </StyledNotFound>
  );
};

export default NotFound;
