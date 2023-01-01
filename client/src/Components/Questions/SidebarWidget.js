import React from 'react';
import styled from 'styled-components';
import favicons from '../../assets/images/favicons-sprite32.png';

const StyledSidebar = styled.aside`
  width: 300px;
  height: fit-content;
  border-radius: 3px;
  background-color: #fef7e3;
  border: 1px solid #f1e5bc;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05), 0 1px 4px rgba(0, 0, 0, 0.05),
    0 2px 8px rgba(0, 0, 0, 0.05);

  .sidebar__articles--header {
    font-size: 12px;
    font-weight: bold;
    background-color: #fbf3d5;
    border-top: 1px solid #f1e5bc;
    border-bottom: 1px solid #f1e5bc;
    color: #3b4045;
    padding: 12px 15px;
    :first-child {
      border-top: 0;
    }
  }
  .sidebar__articles--item {
    padding: 12px 16px;
    display: flex;
    gap: 8px;
    .flexitem1 {
      flex-shrink: 0;
      vertical-align: text-top;
      margin-top: 2px;
    }
    .flexitem2 {
      line-height: 1.4;
      overflow-wrap: break-word;
      color: #0c0d0e;
    }
  }
  .iconGlobe {
    color: #0c0d0e;
  }
  .favicon {
    background: transparent url(${favicons}) no-repeat;
    background-size: 16px 7038px;
    width: 16px;
    height: 16px;
  }
  .favicon-stackexchangemeta {
    background-position: 0 -6120px;
  }
  .favicon-stackoverflowmeta {
    background-position: 0 -6156px;
  }

  @media screen and (max-width: 1200px) {
    display: none;
  }
`;

const SidebarWidget = () => {
  return (
    <StyledSidebar>
      <ul>
        <li className="sidebar__articles--header">The Overflow Blog</li>
        <li className="sidebar__articles--item">
          <div className="flexitem1">
            <svg
              className="svg-icon iconPencilSm"
              width="14"
              height="14"
              viewBox="0 0 14 14"
            >
              <path d="m11.1 1.71 1.13 1.12c.2.2.2.51 0 .71L11.1 4.7 9.21 2.86l1.17-1.15c.2-.2.51-.2.71 0ZM2 10.12l6.37-6.43 1.88 1.88L3.88 12H2v-1.88Z"></path>
            </svg>
          </div>
          <div className="flexitem2">
            <a
              href="https://stackoverflow.blog/2022/12/20/lets-talk-about-our-favorite-terminal-tools/?cb=1"
              target="_blank"
              rel="noreferrer"
            >
              Let’s talk about our favorite terminal tools (Ep. 521)
            </a>
          </div>
        </li>
        <li className="sidebar__articles--item">
          <div className="flexitem1">
            <svg
              className="svg-icon iconPencilSm"
              width="14"
              height="14"
              viewBox="0 0 14 14"
            >
              <path d="m11.1 1.71 1.13 1.12c.2.2.2.51 0 .71L11.1 4.7 9.21 2.86l1.17-1.15c.2-.2.51-.2.71 0ZM2 10.12l6.37-6.43 1.88 1.88L3.88 12H2v-1.88Z"></path>
            </svg>
          </div>
          <div className="flexitem2">
            <a
              href="https://stackoverflow.blog/2022/12/20/best-practices-to-increase-the-speed-for-next-js-apps/?cb=1"
              target="_blank"
              rel="noreferrer"
            >
              Best practices to increase the speed for Next.js apps
            </a>
          </div>
        </li>
        <li className="sidebar__articles--header">Featured on Meta</li>
        <li className="sidebar__articles--item">
          <div className="flexitem1">
            <div className="favicon favicon-stackexchangemeta"></div>
          </div>
          <div className="flexitem2">
            <a
              href="https://meta.stackexchange.com/questions/384169/help-us-identify-new-roles-for-community-members?cb=1"
              target="_blank"
              rel="noreferrer"
            >
              Help us identify new roles for community members
            </a>
          </div>
        </li>
        <li className="sidebar__articles--item">
          <div className="flexitem1">
            <div className="favicon favicon-stackexchangemeta"></div>
          </div>
          <div className="flexitem2">
            <a
              href="https://meta.stackexchange.com/questions/384406/navigation-and-ui-research-starting-soon?cb=1"
              target="_blank"
              rel="noreferrer"
            >
              Navigation and UI research starting soon
            </a>
          </div>
        </li>
        <li className="sidebar__articles--item">
          <div className="flexitem1">
            <div className="favicon favicon-stackoverflowmeta"></div>
          </div>
          <div className="flexitem2">
            <a
              href="https://meta.stackoverflow.com/questions/421619/2022-community-moderator-election-results-now-with-two-more-mods?cb=1"
              target="_blank"
              rel="noreferrer"
            >
              2022 Community Moderator Election Results - now with two more
              mods!
            </a>
          </div>
        </li>
        <li className="sidebar__articles--item">
          <div className="flexitem1">
            <div className="favicon favicon-stackoverflowmeta"></div>
          </div>
          <div className="flexitem2">
            <a
              href="https://meta.stackoverflow.com/questions/422034/proposing-a-community-specific-closure-reason-for-non-english-content?cb=1"
              target="_blank"
              rel="noreferrer"
            >
              Proposing a Community-Specific Closure Reason for non-English
              content
            </a>
          </div>
        </li>
        <li className="sidebar__articles--item">
          <div className="flexitem1">
            <div className="favicon favicon-stackoverflowmeta"></div>
          </div>
          <div className="flexitem2">
            <a
              href="https://meta.stackoverflow.com/questions/421831/temporary-policy-chatgpt-is-banned?cb=1"
              target="_blank"
              rel="noreferrer"
            >
              Temporary policy: ChatGPT is banned
            </a>
          </div>
        </li>
        <li className="sidebar__articles--item">
          <div className="flexitem1">
            <div className="favicon favicon-stackoverflowmeta"></div>
          </div>
          <div className="flexitem2">
            <a
              href="https://meta.stackoverflow.com/questions/422180/im-standing-down-as-a-moderator?cb=1"
              target="_blank"
              rel="noreferrer"
            >
              I&lsquo;m standing down as a moderator
            </a>
          </div>
        </li>
      </ul>
    </StyledSidebar>
  );
};

export default SidebarWidget;
