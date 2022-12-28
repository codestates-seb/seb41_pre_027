const Select = ({ label, value, uniqueArr }) => {
  return (
    <>
      <label>{label}</label>
      <select {...value}>
        {uniqueArr.map((el) => {
          return (
            <option key={el.id} value={el.author}>
              {el.author}
            </option>
          );
        })}
      </select>
    </>
  );
};

export default Select;
