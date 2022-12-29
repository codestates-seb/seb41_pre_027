import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import { Provider } from 'react-redux';
import App from './App';
import GlobalStyle from './assets/style/GlobalStyle';
import store from './Redux/store';
import { CookiesProvider } from 'react-cookie';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <CookiesProvider>
      <Provider store={store}>
        <BrowserRouter>
          <GlobalStyle />
          <App />
        </BrowserRouter>
      </Provider>
    </CookiesProvider>
  </React.StrictMode>
);
