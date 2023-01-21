import { useState } from 'react';

// const useInput = (initialValue) => {
//   const [value, setValue] = useState(initialValue);

//   const onChange = (event) => {
//     const {
//       target: { value },
//     } = event;
//     setValue(value);
//   };

//   return [value, onChange];
// };

// export default useInput;

// import { useState } from 'react';

// const useInput = (initialValue) => {
//   const [value, setValue] = useState(initialValue);

//   const handleChange = (event) => {
//     setValue(value)
//   };

//   return [{value, onChange:handleChange}];
// };

// export default useInput;

function useInput(initialValue) {
  const [value, setValue] = useState(initialValue);
  const reset = () => {
    setValue(initialValue);
  };
  const bind = {
    value,
    onChange: (e) => {
      setValue(e.target.value);
    },
  };
  return [value, bind, reset];
}

export default useInput;
