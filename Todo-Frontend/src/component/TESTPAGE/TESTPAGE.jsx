import React, { useEffect, useState } from 'react';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { jwtDecode } from 'jwt-decode';
import { setStyle } from '../common/CommonFunction';
import { useSelector } from 'react-redux';

//부모가 가정을 무시하면 자식이 자신의 어떤면을 부정하거나 감추는법 배움.
//자신을 있는 그대로 수용하기 어려워짐 자신의 진정한 모'습을 나타내는 것을 
//위험하다고 느끼고 부모가 원하는 모습으로 부모의 기대와 바람에 순응
//자기 수용 3단계 1. 나를 소중히 여기고 존중하기
//2. ㅇ
const TESTPAGE = () => {
    const [user, setUser] = useState({});
    const [inputMsg,setInputMsg] = useState('');
    const [stompClient, setStompClient] = useState(null);
    const [msg, setMsg] = useState([]);
    const darkMode = useSelector((state)=> state.theme.darkMode);
    useEffect(() => {
      const socket = new SockJS(`http://${process.env.REACT_APP_IP}/chat`);

      const stompClient = new Client({
        webSocketFactory: () => socket,
        onConnect: () => {
          console.log('WebSocket connected');
          stompClient.subscribe('/topic/all', function (message) {
            const messageBody = message.body ? JSON.parse(message.body) : null;
            console.log('뭐 옴 : ',messageBody);
            setMsg((prev)=>[...prev,messageBody]);
          });
        },
        onStompError: (frame) => {
          console.error('에러 : ' + frame.headers['message']);
          console.error('추가적인 거 : ' + frame.body);
        },
      });
  
      stompClient.activate();
      setStompClient(stompClient);
      setUser(jwtDecode(localStorage.getItem('token')));

  
      return () => {
        if (stompClient) {
          stompClient.deactivate();
        }
      };
    }, []);
    const sendMsg = () => {
        if(stompClient && stompClient.connected){
            const msg = {
                userCode: user.accountCode,
                userNickname: user.accountNickname,
                message: inputMsg,
            }
            stompClient.publish({
                destination: '/app/all',
                body: JSON.stringify(msg),
                headers: { Authorization: `Bearer ${localStorage.getItem('token')}` },
            });
        }
        setInputMsg('');
    }
    const keyDownHandler = (event) => {
      if(event.key === 'Enter'){
        sendMsg();
      }
    }
  
    return (
      <div>
        <h1>WebSocket Chat</h1>
        <input value={inputMsg} onChange={(e)=>setInputMsg(e.target.value)} onKeyDown={(e)=>keyDownHandler(e)}/>
        <button onClick={sendMsg}>보내기</button>
        {
          msg.length > 0 ? (msg.map((idx,i)=>(<div style={{display:'flex'}}><div style={{color:setStyle(darkMode,'text')}}>{idx.userNickname} :</div><div style={{color:setStyle(darkMode,'text')}} key={i}> {idx.message}</div></div>))) : <></>
        }
      </div>
    );
  };

export default TESTPAGE;