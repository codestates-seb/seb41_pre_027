import React from 'react';
import styled from 'styled-components';

const StyledFooter = styled.footer`
  background-color: #232629;
  color: #9199a1;
  display: flex;
  justify-content: center;

  .contents {
    width: 97.2307692rem;
    height: 100%;
  }
  .flex-vertical-stretch {
    display: flex;
    align-items: stretch;
  }
  .footer {
    padding: 32px 12px 12px 12px;
    flex-direction: row;
    justify-content: space-between;
  }
  .footer__logo {
    flex: 0 0 64px;
  }
  .footer__nav {
    flex: 2 1 auto;
    margin-bottom: 24px;
    > ul {
      display: flex;
    }
    > ul > li {
      font-weight: 700;
      color: #babfc4;
      flex: 1 0 auto;
      padding: 0 12px 24px 0;
    }
    > ul > li > ul {
      margin-top: 12px;
      font-weight: 400;
      color: #9199a1;
      padding: 4px 0;
      line-height: 1.75;
      font-size: 13px;
    }
    .mt16 {
      margin-top: 16px;
    }
  }
  .footer__info {
    font-size: 11px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    flex: 1 1 150px;
  }
  .footer__info--social {
    display: flex;
    flex-direction: row;
    gap: 12px;
  }
  .footer__info--copyright {
    font-size: 11px;
    line-height: 1.45;
    > span {
      text-decoration: underline;
    }
  }

  @media screen and (max-width: 1200px) {
    .contents {
      width: 100%;
    }
    .footer {
      flex-direction: column;
      padding: 12px 24px 32px;
    }
    .footer__nav {
      > ul {
        flex-direction: column;
      }
      > ul > li {
        padding: 0 0 24px 0;
      }
      > ul > li > ul {
        flex-wrap: wrap;
        margin-top: 4px;
        display: flex;
        flex-direction: row;
        gap: 12px;
      }
      .mt16 {
        margin-top: 0;
      }
    }
    .footer__info {
      flex: 1 1 40px;
    }
    .footer__info--copyright {
      br {
        display: none;
      }
    }
  }
  @media screen and (max-width: 640px) {
    .footer {
      padding: 20px 12px;
    }
    .footer__logo {
      display: none;
    }
    .footer__nav {
      > ul {
        flex-direction: column;
      }
      > ul > li {
        padding: 0 0 24px 0;
      }
      > ul > li > ul {
        gap: 12px;
        font-size: 11px;
        line-height: 0.75;
        margin-top: 12px;
      }
    }
    .footer__info {
      flex: 1 1 60px;
    }
    .footer__info--copyright {
      br {
        display: block;
      }
    }
    .pconly {
      display: none !important;
    }
  }
`;
const Footer = () => {
  return (
    <StyledFooter>
      <div className="contents flex-vertical-stretch footer">
        <div className="footer__logo">
          <svg className="svg-icon" width="32" height="37" viewBox="0 0 32 37">
            <path d="M26 33v-9h4v13H0V24h4v9h22Z" fill="#BCBBBB"></path>
            <path
              d="m21.5 0-2.7 2 9.9 13.3 2.7-2L21.5 0ZM26 18.4 13.3 7.8l2.1-2.5 12.7 10.6-2.1 2.5ZM9.1 15.2l15 7 1.4-3-15-7-1.4 3Zm14 10.79.68-2.95-16.1-3.35L7 23l16.1 2.99ZM23 30H7v-3h16v3Z"
              fill="#F48024"
            ></path>
          </svg>
        </div>
        <nav className="footer__nav">
          <ul>
            <li>
              STACK OVERFLOW
              <ul>
                <li>Questions</li>
                <li>Help</li>
              </ul>
            </li>
            <li>
              PRODUCTS
              <ul>
                <li>Teams</li>
                <li>Advertising</li>
                <li>Collectives</li>
                <li>Talent</li>
              </ul>
            </li>
            <li>
              COMPANY
              <ul>
                <li>About</li>
                <li>Press</li>
                <li>Work Here</li>
                <li>Legal</li>
                <li>Privacy Policy</li>
                <li>Terms of Service</li>
                <li>Contact Us</li>
                <li>Cookie Settings</li>
                <li>Cookie Policy</li>
              </ul>
            </li>
            <li>
              STACK EXCHANGE NETWORK
              <ul>
                <li>Technology</li>
                <li>Culture & recreation</li>
                <li>Life & arts</li>
                <li>Science</li>
                <li>Professional</li>
                <li>Business</li>
                <li className="mt16">API</li>
                <li>Data</li>
              </ul>
            </li>
          </ul>
        </nav>
        <div className="footer__info">
          <ul className="footer__info--social">
            <li>Blog</li>
            <li>Facebook</li>
            <li>Twitter</li>
            <li>LinkdIn</li>
            <li>Instagram</li>
          </ul>
          <div className="footer__info--copyright">
            Site design / logo © 2022 Stack Exchange Inc; user
            <br /> contributions licensed under <span>CC BY-SA</span>.
            <br className="pconly" /> rev 2022.12.21.43127
          </div>
        </div>
      </div>
    </StyledFooter>
  );
};

export default Footer;
