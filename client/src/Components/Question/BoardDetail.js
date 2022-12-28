import { useParams } from 'react-router-dom';
import Loading from '../Loading';
import { fetchDelete } from '../../utils/api';
import useFetch from '../../utils/useFetch';
import useScrollTop from '../../utils/useScrollTop';
import styled from 'styled-components';

const Content = styled.div`
  .title__detail {
    font-size: var(--fs-headline1);
    font-family: var(--theme-post-title-font-family);
    line-height: 1.35;
    font-weight: normal;
    margin-bottom: 0;
  }
`;
const Clicks = styled.div``;

function BoardDetail() {
  const { id } = useParams();

  const [board, isPending, error] = useFetch(
    `http://localhost:4000/boards/${id}`
  );
  useScrollTop();

  const handleDelete = () => {
    fetchDelete(`http://localhost:4000/boards`, id);
  };

  // const handleUpdate = () => {
  //   let data = {}; //사용자가 업데이트 한 내용
  //   fetchPatch('http://localhost:4000/boards', id, data);
  // };
  return (
    <>
      <div className="board__details">
        {isPending && <Loading />}
        {error && <div>{error}</div>}
        {board && (
          <article>
            <Content>
              <div>
                <h2 className="title__detail">{board.title}</h2>
              </div>
              <div>
                <h3 className="content__detail">{board.body}</h3>
              </div>
            </Content>
            {/* <button onClick={handleUpdate}>게시글 수정하기</button> */}
          </article>
        )}
        <Clicks>
          <div>
            <button>Share</button>
          </div>
          <div>
            <button>Edit</button>
          </div>
          <div>
            <button>Follow</button>
          </div>
          <button onClick={handleDelete}>게시글 삭제하기</button>
        </Clicks>
      </div>
    </>
  );
}

export default BoardDetail;
