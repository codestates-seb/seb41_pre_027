import { useSelector } from 'react-redux';
import { useNavigate, useParams } from 'react-router-dom';
import { useCallback, useEffect, useState } from 'react';
import axios from 'axios';

const EditBoard = ({ boards, setBoard }) => {
  const token = useSelector((state) => state.Auth.token);
  const navigate = useNavigate();
  const { id } = useParams();

  return (
    <>
      <div>편집하기 속 파일</div>
    </>
  );
};

export default EditBoard;
