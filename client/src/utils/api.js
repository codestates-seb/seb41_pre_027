//api 문서화
import axios from 'axios';

const BASE_URL = 'http://localhost:3000/';
const BOARD_URL = 'http://localhost:3000/boards/';

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
  fetch(`${url}${id}`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data),
  })
    .then(() => {
      window.location.href = `${BOARD_URL}${id}`;
    })
    .catch((error) => {
      console.error('Error', error);
    });
};

export const fetchDeleteComment = (url, id) => {
  fetch(`${url}${id}`, {
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
