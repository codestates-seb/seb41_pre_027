import React, { useState, useEffect } from 'react';
import { Routes, Route, useLocation } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import { windowWidthActions } from './Redux/windowWidth';
import { debounce } from 'lodash';
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

  return (
    <StyledApp isFillBg={isFillBg}>
      <Header />
      <MainContainer>
        {/* GNB를 보여주는 location이고 모바일 사이즈가 아닐 때 컴포넌트 사용 */}
        {isSideNavShow && windowWidth > 640 && <GNB />}
        <Routes>
          <Route exact path="/" element={<Questions />} />
          <Route exact path="/questions/search" element={<SearchResult />} />
          <Route
            exact
            path="/questions/tagged/:tag"
            element={<QuestionsTagged />}
          />
          <Route path="/questions/:questionId" element={<QuestionView />} />
          <Route exact path="/questions/ask" element={<AskAQuestion />} />
          <Route path="/tags" element={<Tags />} />
          <Route exact path="/users" element={<Users />} />
          <Route path="/users/:memberId" element={<UserView />} />
          <Route path="/mypage" element={<MyPage />} />
          <Route path="/login" element={<LogIn />} />
          <Route path="/signup" element={<SignUp />} />
          <Route path="*" element={<NotFound />} />
        </Routes>
      </MainContainer>
      {/* Footer를 보여주는 location일 때 컴포넌트 사용 */}
      {isFooterShow && <Footer />}
    </StyledApp>
  );
}

export default App;
