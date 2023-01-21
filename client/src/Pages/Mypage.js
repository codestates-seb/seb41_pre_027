import React, { useState, useCallback, Link } from 'react';
import styled from 'styled-components';
import EditProfile from './EditProfile';
import cake from '../Img/cake.png';
import calender from '../Img/calendar.png';
import clock from '../Img/clock.png';
import pencil from '../Img/pencil.png';
import NewestPosts from '../Components/Users/NewestPosts';

const Main = styled.div`
  display: flex;
  justify-content: center;
  flex-direction: column;
`;
const Content = styled.div`
  display: flex;
  flex-direction: column;
  width: 1050px;
  padding: 15px;
`;
const Head = styled.div`
  display: flex;
  width: 100%;
  margin-bottom: 10px;
  justify-content: space-between;
`;
const Tabs = styled.div`
  display: flex;
`;
const Tab = styled.div`
  text-decoration: none;
  margin: 0.5px;
  padding: 5px 10px;
  font-size: 1.3rem;
  color: #656b71;
  &:hover {
    background-color: #f1f2f3;
    border-radius: 30px;
  }
  &.clicked {
    background-color: #f1823b;
    border-radius: 30px;
    color: white;
  }
  &.clicked::after {
    background-color: #f1823b;
    border-radius: 30px;
    color: white;
  }

  cursor: pointer;
`;
const H1 = styled.h1`
  font-size: 3.5rem;
  margin-left: 10px;
`;
const Detail = styled.div`
  color: #9ba2a9;
  cursor: pointer;
  white-space: nowrap;
  display: flex;
  margin: 15px 5px;
  font-size: 1.5rem;
  margin: 20px;
`;
const Details = styled.div`
  margin: 5px 20px;
  justify-content: center;
  display: flex;
  flex-direction: column;
`;
const Icon = styled.div`
  margin: 0 5px 0 10px;
  .cake {
    width: 2rem;
    height: 2rem;
    margin-right: 5px;
    vertical-align: top;
  }
  .clock {
    width: 1.6rem;
    height: 1.6rem;
    margin-right: 5px;
    vertical-align: top;
  }
  .calendar {
    width: 1.5rem;
    height: 1.5rem;
    margin-right: 7px;
    vertical-align: top;
  }
`;

const Buttons = styled.div`
  display: flex;
  height: 35px;
`;
const Button = styled.button`
  font-size: 1.4rem;
  border-radius: 3px;
  display: flex;
  justify-content: center;
  align-items: center;
  white-space: nowrap;
  padding: 7px 10px;
  &:hover {
    background-color: #f8f9f9;
  }
  text-decoration: none;
  margin: 5 5 5 5px;
  color: #9ba2a9;
  background-color: white;
  border-color: #9ba2a9;
  border: 1px solid;
  cursor: pointer;
  .pencil {
    width: 1.5rem;
    height: 1.5rem;
    margin-right: 8px;
  }
`;
const Left = styled.div`
  display: flex;
`;

function Mypage() {
  return (
    <Main>
      <Content>
        <Head>
          <Left>
            image
            <Details>
              <H1>nickname</H1>
              <Detail>
                <Icon>
                  <img src={cake} alt="cake" className="cake" />
                  Member for 1 days
                </Icon>
                <Icon>
                  <img src={clock} alt="clock" className="clock" />
                  Last seen this week
                </Icon>
                <Icon>
                  <img src={calender} alt="clock" className="calendar" />
                  Visited 1 days, 3 consecutive
                </Icon>
              </Detail>
            </Details>
          </Left>

          <Buttons>
            <Button as="a" href="mypage/editprofile">
              <img src={pencil} alt="pencil" className="pencil" />
              Edit profile
            </Button>
          </Buttons>
        </Head>
        <Tabs>
          <Tab>Profile</Tab>

          <Tab>Activity</Tab>

          <Tab>Saves</Tab>

          <Tab>Settings</Tab>
        </Tabs>
      </Content>
      <NewestPosts />
    </Main>
  );
}

export default Mypage;
