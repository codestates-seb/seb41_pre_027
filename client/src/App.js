import React, { useState, useEffect } from 'react';
import { Routes, Route, useLocation } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import { windowWidthActions } from './Redux/windowWidth';
import { debounce } from 'lodash';
import useFetch from './utils/useFetch'; //cr추가
import useScrollTop from './utils/useScrollTop';
import Header from './Components/UI/Header';
import GNB from './Components/UI/GNB';
import Questions from './Pages/Questions';
import Tags from './Pages/Tags';
import Users from './Pages/Users';
import MyPage from './Pages/MyPage';
import LogIn from './Pages/LogIn';
import SignUp from './Pages/SignUp';
import AskAQuestion from './Pages/AskAQuestion';
import Footer from './Components/UI/Footer';
import QuestionView from './Pages/QuestionView';
import SearchResult from './Pages/SearchResult';
import QuestionsTagged from './Pages/QuestionsTagged';
import UserView from './Pages/UserView';
import NotFound from './Pages/NotFound';
import QuestionList from './Pages/Questions/QuestionList'; //cr추가
import AskQuestions from './Pages/Questions/AskQuestions'; //cr추가
import QuestionDetail from './Pages/Questions/QuestionDetail';//cr추가
import EditQuestion from './Pages/Questions/EdItQuestion';//cr추가
import styled from 'styled-components';

const StyledApp = styled.div`
  width: 100%;
  background-color: ${(props) => (props.isFillBg ? '#f1f2f3' : 'transparent')};
`;
const MainContainer = styled.main`
  width: 97.2307692rem;
  margin: 0 auto;
  padding-top: 53px;
  display: flex;

  @media screen and (max-width: 1200px) {
    width: 100%;
  }
`;

function App() {
  const [isFillBg, setIsFillBg] = useState(false);
  // 윈도우 가로 길이가 변경되면 windowWidth 상태를 변경한다.
  const dispatch = useDispatch();
  const windowWidth = useSelector((state) => state.windowWidth.width);

  const handleResize = debounce(() => {
    dispatch(windowWidthActions.windowResize(window.innerWidth));
  }, 500);

  useEffect(() => {
    window.addEventListener('resize', handleResize);
    return () => {
      window.removeEventListener('resize', handleResize);
    };
  }, []);

  // location의 path에 따라 sideNav나 Footer를 보여주는 상태 변경 함수
  const location = useLocation().pathname;
  const [isSideNavShow, setIsSideNavShow] = useState(true);
  const [isFooterShow, setIsFooterShow] = useState(true);
  useEffect(() => {
    if (
      location === '/login' ||
      location === '/signup' ||
      location === '/not-found-page'
    ) {
      setIsSideNavShow(false);
      setIsFillBg(true);
      setIsFooterShow(false);
    } else if (location === '/questions/ask') {
      setIsSideNavShow(false);
      setIsFillBg(true);
    } else {
      setIsSideNavShow(true);
      setIsFillBg(false);
    }
  }, [location]);

//cr추가
  const [boards, isPending, error, setBoard] = useFetch(
    'http://localhost:4000/boards/'
  );
  useScrollTop();
  return (
    <StyledApp isFillBg={isFillBg}>
      <Header />
      <MainContainer>
        {/* GNB를 보여주는 location이고 모바일 사이즈가 아닐 때 컴포넌트 사용 */}
        {isSideNavShow && windowWidth > 640 && <GNB />}
        <Routes>
          <Route exact path="/" element={<Questions />} /> //질문 목록들
          <Route exact path="/questions/search" element={<SearchResult />} /> //질문에 대한 검색
          <Route
            exact
            path="/questions/tagged/:tag"
            element={<QuestionsTagged />}
          /> //태그를 눌렀을 때 태그 검색하는 질문들
          <Route path="/questions/:questionId" element={<QuestionView />} /> //각각 질문 id에 대해서 보여주는 상세페이지
          <Route exact path="/questions/ask" element={<AskAQuestion />} /> //질문 생성 
          <Route path="/tags" element={<Tags />} /> //태그 목록들
          <Route exact path="/users" element={<Users />} /> //유저목록
          <Route path="/users/:memberId" element={<UserView />} /> //개별 유저 보기
          <Route path="/mypage" element={<MyPage />} /> //마이페이지
          <Route path="/login" element={<LogIn />} /> //로그인 페이지
          <Route path="/signup" element={<SignUp />} /> //회원가입 페이지
          <Route
            path="/main"
            element={<QuestionList boards={boards} isPending={isPending} />}
          /> //생성한 게시물 목록들
          <Route path="/create" element={<AskQuestions boards={boards} />} /> //
          <Route
            path="/boards/:id"
            element={<QuestionDetail setBoard={setBoard} />}
          /> //등록된 게시물 하나하나 페이지
          <Route
            path="/edit/:id"
            element={<EditQuestion boards={boards} setBoard={setBoard} />}
          /> // 편집 페이지
          <Route path="*" element={<NotFound />} /> //잘못된 경로 접속했을 때 
        </Routes>
      </MainContainer>
      {/* Footer를 보여주는 location일 때 컴포넌트 사용 */}
      {isFooterShow && <Footer />}
    </StyledApp>
  );
}

export default App;
