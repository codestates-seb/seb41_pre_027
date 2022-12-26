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
import styled from 'styled-components';

const StyledApp = styled.div`
  width: 100%;
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

  // location의 path가 /login, /signup이 아닐 경우에만 GNB를 화면 좌측에 보여주기 위한 상태 변경 함수
  const location = useLocation().pathname;
  const [isShow, setIsShow] = useState(true);
  useEffect(() => {
    if (
      location === '/login' ||
      location === '/signup' ||
      location === '/questions/ask'
    ) {
      setIsShow(false);
    } else {
      setIsShow(true);
    }
  }, [location]);

  return (
    <StyledApp>
      <Header />
      <MainContainer>
        {/* GNB를 보여주는 location이고 모바일 사이즈가 아닐 때 컴포넌트 사용 */}
        {isShow && windowWidth > 640 && <GNB />}
        <Routes>
          <Route path="/" element={<Questions />} />
          <Route path="/tags" element={<Tags />} />
          <Route path="/users" element={<Users />} />
          <Route path="/mypage" element={<MyPage />} />
          <Route path="/login" element={<LogIn />} />
          <Route path="/signup" element={<SignUp />} />
          <Route path="/questions/ask" element={<AskAQuestion />} />
        </Routes>
      </MainContainer>
      <Footer />
    </StyledApp>
  );
}

export default App;
