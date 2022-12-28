import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import useFetch from './utils/useFetch';
import useScrollTop from './utils/useScrollTop';
import GlobalStyle from './assets/style/GlobalStyle';

import QuestionList from './Pages/Questions/QuestionList';
import AskQuestions from './Pages/Questions/AskQuestions';
import QuestionDetail from './Pages/Questions/QuestionDetail';
import EditQuestion from './Pages/Questions/EdItQuestion';

function App() {
  const [boards, isPending, error, setBoard] = useFetch(
    'http://localhost:4000/boards/'
  );
  useScrollTop();
  return (
    <div>
      <GlobalStyle />
      <BrowserRouter>
        <Routes>
          <Route
            path="/"
            element={<QuestionList boards={boards} isPending={isPending} />}
          />
          <Route path="/create" element={<AskQuestions boards={boards} />} />
          <Route
            path="/boards/:id"
            element={<QuestionDetail setBoard={setBoard} />}
          />
          <Route
            path="/edit/:id"
            element={<EditQuestion boards={boards} setBoard={setBoard} />}
          />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
