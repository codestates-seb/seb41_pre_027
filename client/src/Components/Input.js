const Input = ({ label, value }) => {
  return (
    <>
      <label>{label}</label>
      {label === '제목' ? (
        <input
          type="text"
          required
          {...value}
          placeholder={`${label}을 입력해주세요.`}
        />
      ) : (
        <textarea
          type="text"
          required
          {...value}
          placeholder={`${label}을 입력해주세요.`}
        />
      )}
    </>
  );
};

export default Input;
