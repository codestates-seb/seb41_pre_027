import { Link } from 'react-router-dom';
import styled from 'styled-components';
import useScrollTop from '../../utils/useScrollTop';

const Container = styled.div`
  border: 1px solid #edeff1;
  display: flex;
  height: 100px;
  .board__list__main {
    margin-left: 90px;
  }
  .board__list__title {
    font-size: 14px;
    margin-top: 1rem;
    margin-bottom: 0.3846rem;
    padding-right: var(--su24);
    line-height: var(--lh-md);
    color: #5798d6;
    line-height: 1rem;
    text-decoration-line: none;
    overflow: hidden;
    text-overflow: ellipsis;
    word-wrap: break-word;
    display: -webkit-box;
    text-align: left;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
  }
  .board__list__content {
    font-size: 8px;
    margin-top: 0.8rem;
    color: var(--fc-medium);
    line-height: 1rem;
    overflow: hidden;
    text-overflow: ellipsis;
    word-wrap: break-word;
    display: -webkit-box;
    text-align: left;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
  }
`;

function BoardList({ boards, isPending }) {
  useScrollTop();
  return (
    <>
      <div className="board__list">
        {/* {boards.sort((a, b) => b.order - a.order)} */}
        {boards.map((board) => (
          <div className="board__preview" key={board.id}>
            <Container>
              <Link to={`/boards/${board.id}`}>
                <div className="board__list__main">
                  <div className="board__list__title">{board.title}</div>
                  <div className="board__list__content">{board.body}</div>
                </div>
              </Link>
            </Container>
          </div>
        ))}
      </div>
    </>
  );
}
export default BoardList;
