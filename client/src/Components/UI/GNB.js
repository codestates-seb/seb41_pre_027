import React, { useEffect, useState } from 'react';
import { NavLink, useLocation } from 'react-router-dom';
import styled from 'styled-components';

const StyledGNB = styled.nav`
  width: 164px;
  overflow-y: auto;
  flex-shrink: 0;
  padding-top: 24px;
  height: 100%;

  .flex-vertical-center {
    display: flex;
    align-items: center;
  }

  .gnb__header {
    margin-bottom: 2px;
    color: #6a737c;
    font-size: 11px;
    line-height: 2;
    padding-left: 6px;
  }

  ul {
    li {
      a {
        display: block;
        padding: 8px 4px 8px 30px;
        line-height: 2;
        color: #525960;
        cursor: pointer;
        :hover {
          color: #0c0d0e;
        }
      }
      .active {
        color: #0c0d0e;
        font-weight: bold;
        background-color: #f1f2f3;
        border-right: 3px solid #f48225;
      }
    }
    li:first-child a {
      padding: 8px 6px;
    }
  }

  .iconGlobe {
    color: #0c0d0e;
    margin-right: 4px;
    margin-top: 2px;
  }
`;
const GNB = () => {
  const location = useLocation().pathname;
  const [isQuestions, setIsQuestions] = useState(false);
  useEffect(() => {
    if (location.includes('/questions')) {
      setIsQuestions(true);
    } else {
      setIsQuestions(false);
    }
  }, [location]);

  const questionsElement = () => {
    return (
      <span className="flex-vertical-center">
        <svg
          className="svg-icon iconGlobe"
          width="18"
          height="18"
          viewBox="0 0 18 18"
        >
          <path d="M9 1C4.64 1 1 4.64 1 9c0 4.36 3.64 8 8 8 4.36 0 8-3.64 8-8 0-4.36-3.64-8-8-8ZM8 15.32a6.46 6.46 0 0 1-4.3-2.74 6.46 6.46 0 0 1-.93-5.01L7 11.68v.8c0 .88.12 1.32 1 1.32v1.52Zm5.72-2c-.2-.66-1-1.32-1.72-1.32h-1v-2c0-.44-.56-1-1-1H6V7h1c.44 0 1-.56 1-1V5h2c.88 0 1.4-.72 1.4-1.6v-.33a6.45 6.45 0 0 1 3.83 4.51 6.45 6.45 0 0 1-1.51 5.73v.01Z"></path>
        </svg>
        Questions
      </span>
    );
  };

  const gnbArr = [
    { param: '/', content: questionsElement() },
    { param: '/tags', content: 'Tags' },
    { param: '/users', content: 'Users' },
  ];

  return (
    <StyledGNB>
      <p className="gnb__header">PUBLIC</p>
      <ul>
        {gnbArr.map((el, index) => {
          return (
            <li key={index}>
              <NavLink
                to={el.param}
                className={isQuestions && index === 0 ? 'active' : null}
                onClick={window.location.reload}
              >
                {el.content}
              </NavLink>
            </li>
          );
        })}
      </ul>
    </StyledGNB>
  );
};

export default GNB;
