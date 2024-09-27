import React, { useEffect, useRef, useState } from 'react';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { jwtDecode } from 'jwt-decode';
import { setStyle } from '../common/CommonFunction';
import { useSelector } from 'react-redux';
import api from '../../AxiosInterceptor';
import BackButton from "../common/BackButton";


const Chat = () => {
    const [user, setUser] = useState({});
    const [inputMsg,setInputMsg] = useState('');
    const [stompClient, setStompClient] = useState(null);
    const [msg, setMsg] = useState([]);
    const [page, setPage] = useState(0);
    const darkMode = useSelector((state)=> state.theme.darkMode);
    const chatRef = useRef(null);
    useEffect(() => {
      test();
      const socket = new SockJS(`http://${process.env.REACT_APP_IP}/chat`);

      const stompClient = new Client({
        webSocketFactory: () => socket,
        onConnect: () => {
          console.log('채팅 서버에 연결되었습니다.');
          
          stompClient.subscribe('/topic/all', function (message) {
            const messageBody = message.body ? JSON.parse(message.body) : null;
            const date = new Date(messageBody.time);
            const formattedTime = [
              date.getFullYear(),
              date.getMonth() + 1,
              date.getDate(),
              date.getHours(),
              date.getMinutes(),
              date.getSeconds(),
            ];            
            setMsg((prev)=>[...prev,{...messageBody, time: formattedTime}]);
            setTimeout(()=>{
              downScroll();
            },300)
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
      let newMsg = [];
      let len = res.data.content.length;
      for(var i=1; i<=res.data.content.length; i++){
        let nickname = res.data.content[len-i].user.userNickname;
        let message = res.data.content[len-i].chatMessage;
        let time = res.data.content[len-i].chatTime;

        let msgBody = {userNickname:nickname,message:message,time:time};
        newMsg = [...newMsg, msgBody];
      }
      setMsg((prev)=>[...newMsg, ...prev]); 
    }
    const scrollDataLoad = () => {
      if(chatRef.current.scrollTop === 0){
        test();
      }
    }
    const [firstData, setFirstData] = useState(true);
    useEffect(()=>{
      if(msg.length>0){
        if(firstData){
          downScroll();
          setFirstData(false);
        }
      } else {
        downScroll();
      }
    },[msg])
    const downScroll = () => {
      const table = chatRef.current;
      table.scrollTop = table.scrollHeight;
    }
    return (
      <div>
        <BackButton moveTo="/todo"/>
        <h1 style={{color:setStyle(darkMode,'text')}}>채팅방</h1>
        <div ref={chatRef} onScroll={scrollDataLoad} style={{height:200,overflowY:'scroll'}}>
        <table>
          <tbody>
        {msg.length > 0 ? (
          msg.map((idx, i) => (
            <tr key={i}>
              <td style={{ color: setStyle(darkMode, 'text') }}>{idx.userNickname} :</td>
              <td style={{ color: setStyle(darkMode, 'text'), paddingRight:20}}>{idx.message}</td>
              <td style={{ color: setStyle(darkMode, 'text') }}>
                {`${idx.time[1]}/${idx.time[2]} - ${idx.time[3]}:${idx.time[4]}:${idx.time[5]}`}
              </td>
            </tr>
          ))
        ) : (
            <tr>
              <td>채팅 내역이 없습니다.</td>
            </tr>
          )}
          </tbody>
        </table>
        </div>
        <input value={inputMsg} onChange={(e)=>setInputMsg(e.target.value)} onKeyDown={(e)=>keyDownHandler(e)}/>
        <button onClick={sendMsg}>보내기</button>
        {/* <button onClick={test}>테스트 버튼</button> */}
      </div>
    );
  };

export default Chat;