import React, { useEffect, useState } from 'react';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { jwtDecode } from 'jwt-decode';
import { setStyle } from '../common/CommonFunction';
import { useSelector } from 'react-redux';
import api from '../../AxiosInterceptor';

const TESTPAGE = () => {
    const [user, setUser] = useState({});
    const [inputMsg,setInputMsg] = useState('');
    const [stompClient, setStompClient] = useState(null);
    const [msg, setMsg] = useState([]);
    const [page, setPage] = useState(0);
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

    const test = async () => {
      setPage(page+1);
      const res = await api.get(`/chat?page=${page}`);
      console.log(res.data);
      for(var i=0; i<res.data.content.length; i++){
        let msgBody = {userNickname:res.data.content[i].user.userNickname,message:res.data.content[i].chatMessage}                
        setMsg((prev)=> [...prev, msgBody]);
      }
      
    }
  
    return (
      <div>
        <h1>WebSocket Chat</h1>
        <input value={inputMsg} onChange={(e)=>setInputMsg(e.target.value)} onKeyDown={(e)=>keyDownHandler(e)}/>
        <button onClick={sendMsg}>보내기</button>
        <button onClick={test}>테스트 버튼</button>
        {
          msg.length > 0 ? (msg.map((idx,i)=>(<div style={{display:'flex'}}><div style={{color:setStyle(darkMode,'text')}}>{idx.userNickname} :</div><div style={{color:setStyle(darkMode,'text')}} key={i}> {idx.message}</div></div>))) : <></>
        }
      </div>
    );
  };

export default TESTPAGE;