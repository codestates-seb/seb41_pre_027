import React, { useState, useEffect } from 'react';
import { Routes, Route, useLocation } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import { windowWidthActions } from './Redux/windowWidth';
import { debounce } from 'lodash';
import useScrollTop from './utils/useScrollTop';
import Header from './Components/UI/Header';
import GNB from './Components/UI/GNB';
import Questions from './Pages/Questions';
import Tags from './Pages/Tags';
import Users from './Pages/Users';
import Login from './Pages/Login';
import SignUp from './Pages/SignUp';
import AskAQuestion from './Pages/AskAQuestion';
import Footer from './Components/UI/Footer';
import SearchResult from './Pages/SearchResult';
import QuestionsTagged from './Pages/QuestionsTagged';
import UserView from './Pages/UserView';
import NotFound from './Pages/NotFound';
import QuestionDetail from './Pages/Questions/QuestionDetail'; //cr추가
import PatchQuestion from './Pages/Questions/PatchQuestion';
import styled from 'styled-components';
import { authActions } from './Redux/auth';
import { Cookies } from 'react-cookie';
import Editprofile from './Pages/EditProfile';
import MyPageView from './Pages/Mymypage';

const StyledApp = styled.div`
  width: 100%;
  background-color: ${(props) =>
    props.isFillBg === 'gray'
      ? '#f1f2f3'
      : props.isFillBg === 'lightgray'
      ? '#f8f9f9'
      : 'transparent'};
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
  const cookies = new Cookies();
  useEffect(() => {
    const memberId = cookies.get('memberId');
    if (memberId) {
      dispatch(authActions.login());
    } else {
      dispatch(authActions.logout());
    }
  }, []);
  const [isFillBg, setIsFillBg] = useState('default');
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
      setIsFillBg('gray');
      setIsFooterShow(false);
    } else if (location === '/questions/ask') {
      setIsSideNavShow(false);
      setIsFillBg('lightgray');
    } else {
      setIsSideNavShow(true);
      setIsFillBg('default');
    }
  }, [location]);

  useScrollTop();
  return (
    <StyledApp isFillBg={isFillBg}>
      <Header />
      <MainContainer>
        {/* GNB를 보여주는 location이고 모바일 사이즈가 아닐 때 컴포넌트 사용 */}
        {isSideNavShow && windowWidth > 640 && <GNB />}
        <Routes>
          <Route exact path="/" element={<Questions />} /> {/*질문 목록들*/}
          <Route
            exact
            path="/questions/search"
            element={<SearchResult />}
          />{' '}
          {/*질문에 대한 검색*/}
          <Route
            exact
            path="/questions/tagged/:tag"
            element={<QuestionsTagged />}
          />{' '}
          {/* 태그를 눌렀을 때 태그 검색하는 질문들 */}
          <Route exact path="/questions/ask" element={<AskAQuestion />} />
          <Route path="/questions/:id" element={<QuestionDetail />} />
          <Route path="/patch/:id" element={<PatchQuestion />} />
          {/* 질문 생성 */}
          <Route path="/tags" element={<Tags />} /> {/* 태그 목록들*/}
          <Route exact path="/users" element={<Users />} /> {/* 유저 목록 */}
          <Route path="/users/:memberId" element={<UserView />} />
          {/* 개별 유저 보기 */}
          <Route path="/mypage" element={<MyPageView />} /> {/* 마이페이지 */}
          <Route path="/login" element={<Login />} /> {/* 로그인 페이지 */}
          <Route path="/signup" element={<SignUp />} /> {/* 회원가입 페이지 */}
          <Route path="/mypage/editprofile" element={<Editprofile />} />
          {/* 편집하기 */}
          <Route path="*" element={<NotFound />} />{' '}
          {/* 잘못된 경로 접속했을 때 */}
        </Routes>
      </MainContainer>
      {/* Footer를 보여주는 location일 때 컴포넌트 사용 */}
      {isFooterShow && <Footer />}
    </StyledApp>
  );
}

export default App;
