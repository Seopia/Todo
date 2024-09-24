import React, { useEffect, useState } from 'react';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

const TESTPAGE = () => {
    const [stompClient, setStompClient] = useState(null);
  
    useEffect(() => {
      const socket = new SockJS(`http://${process.env.REACT_APP_IP}/chat`);

      const stompClient = new Client({
        webSocketFactory: () => socket,
        onConnect: () => {
          console.log('WebSocket connected');
          stompClient.subscribe('/topic/all', (msg) => {
            console.log(msg);
            
          })
        },
        onStompError: (frame) => {
          console.error('에러 : ' + frame.headers['message']);
          console.error('추가적인 거 : ' + frame.body);
        },
      });
  
      stompClient.activate();
      setStompClient(stompClient);
  
      return () => {
        if (stompClient) {
          stompClient.deactivate();
        }
      };
    }, []);

    const sendMsg = () => {
        if(stompClient && stompClient.connected){
            console.log('보낸다');
            const msg = {
                userNickname: 'ddd',
                message: '메세지',
            }
            stompClient.publish({
                destination: '/app/all',
                body: JSON.stringify(msg),
                headers: { Authorization: `Bearer ${localStorage.getItem('token')}` },
            });
            
        }
    }
  
    return (
      <div>
        <h1>WebSocket Chat</h1>
        <button onClick={sendMsg}>보내기</button>
      </div>
    );
  };

export default TESTPAGE;