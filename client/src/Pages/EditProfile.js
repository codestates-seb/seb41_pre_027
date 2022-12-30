import styled from 'styled-components';

const Container = styled.div``;
const Span = styled.span`
  color: #7e868e;
  font-size: 1.3rem;
  margin-left: 10px;
`;
const Title = styled.h1`
  font-weight: 400;
  font-size: 2.6rem;
  margin-top: 20px;
`;
const Hr = styled.hr`
  width: 1000px;
  height: 1px;
  margin: 20px 0;
  border: 1px solid rgb(216, 217, 220);
`;
const Inform = styled.h4`
  margin-top: 20px;
  margin-bottom: 10px;
  font-size: 2rem;
`;
const PubBox = styled.div`
  border: 1px solid rgb(216, 217, 220);
  padding: 20px;
`;
const SubTitle = styled.h6`
  font-weight: 500;
  margin: 10px 0;
  font-size: 1.4rem;
`;
const Input = styled.input`
  border: 1px solid rgb(216, 217, 220);
  width: 100%;
  max-width: 800px;
`;
const Buttons = styled.div`
  display: flex;
  margin: 10px;
`;
const Button1 = styled.button`
  padding: 10px;
  color: white;
  text-decoration: none;
  background-color: #379fef;
  border-radius: 3px;
  border: none;
  margin-right: 10px;
  cursor: pointer;
  &:hover {
    background-color: hsl(206, 100%, 40%);
  }
`;
const Button2 = styled.button`
  color: #379fef;
  padding: 10px 15px;
  border: none;
  background-color: white;
  border-radius: 3px;
  cursor: pointer;
  &:hover {
    background-color: #efefef;
  }
`;
function Editprofile() {
  return (
    <Container>
      <Title>Edit your profile</Title>
      <Hr />
      <Inform>Public information</Inform>
      <PubBox>
        <SubTitle>Profile image</SubTitle>

        <SubTitle>Display name</SubTitle>
        <Input />
      </PubBox>
      <Inform>
        Private information<Span>Not shown publicly</Span>
      </Inform>
      <PubBox>
        <SubTitle>Full name</SubTitle>
        <Input />
      </PubBox>
      <Buttons>
        <Button1>Save Edits</Button1>
        <Button2>Cancel</Button2>
      </Buttons>
    </Container>
  );
}

export default Editprofile;
