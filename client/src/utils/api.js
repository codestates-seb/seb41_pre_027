//api 문서화
import axios from 'axios';
import { Cookies } from 'react-cookie';
const cookies = new Cookies();
const accessToken = cookies.get('Authorization');

const BASE_URL = `/`;
const BOARD_URL = '/api/questions/';

export const fetchCreate = (url, data) => {
  fetch(url, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data),
  })
    .then(() => {
      window.location.href = BASE_URL;
    })
    .catch((error) => {
      console.error('Error', error);
    });
};

export const fetchDelete = (url, id) => {
  fetch(`${url}${id}`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
    },
  })
    .then(() => {
      window.location.href = BASE_URL;
    })
    .catch((error) => {
      console.error('Error', error);
    });
};

export const fetchPatch = (url, id, data) => {
  fetch(`${url}${id}`, {
    method: 'PATCH',
    headers: { 'Content-Type': 'Application/json' },
    body: JSON.stringify(data),
  })
    .then(() => {
      window.location.href = `${BOARD_URL}${id}`;
    })
    .catch((error) => {
      console.error('Error', error);
    });
};

export const fetchCreateComment = (url, id, data) => {
  fetch(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ` + accessToken.slice(7),
    },
    body: JSON.stringify(data),
  })
    .then(() => {
      window.location.href = `/api/questions/${id}`;
    })
    .catch((error) => {
      console.error('Error', error);
    });
};

export const fetchDeleteComment = (url, id) => {
  fetch(url, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
    },
  })
    .then(() => {
      window.location.href = `${BOARD_URL}${id}`;
    })
    .catch((error) => {
      console.error('Error', error);
    });
};

export const fetchPatchComment = (url, id, data) => {
  fetch(`${url}${id}`, {
    method: 'PATCH',
    headers: { 'Content-Type': 'Application/json', Authorization: accessToken },
    body: JSON.stringify(data),
  })
    .then(() => {
      window.location.href = `${BOARD_URL}${id}`;
    })
    .catch((error) => {
      console.error('Error', error);
    });
};
