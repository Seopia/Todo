import axios from "axios";
import { useEffect, useState } from "react";
import './Todo.css';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faChevronLeft, faChevronRight, faCirclePlus } from "@fortawesome/free-solid-svg-icons";
import { useNavigate } from "react-router-dom";
import TaskList from "./TaskList";
import api from "../../AxiosInterceptor";
import { jwtDecode } from "jwt-decode";
import ProfileImage from "../common/ProfileImage";
import { useSelector } from "react-redux";
import { darkModeText, setStyle } from "../common/CommonFunction";

const Todo = () => {
    const darkMode = useSelector((state) => state.theme.darkMode);
    const nav = useNavigate();
    const [user, setUser] = useState({});
    const [categoryNames, setCategoryNames] = useState([]);
    const [taskList, setTaskList] = useState([]);
    const [currentCategory, setCurrentCategory] = useState("전체");
    const [selectedDay, setSelectedDay] = useState(new Date());
    const [taskLoad, setTaskLoad] = useState(false);
    const [taskOfMonth, setTaskOfMonth] = useState([]);
    const [newTask, setnewTask] = useState({
        taskCode:1,
        taskContent: '',
        taskStartDate: '',
        taskEndDate: '',
        taskState: false,
        taskUserName: '',
        taskCategoryName: ''
    })

    /* 캘린더 계산 */
    const [currentDate, setCurrentDate] = useState(new Date());
    const daysOfWeek = ['일', '월', '화', '수', '목', '금', '토'];
    const formatter = new Intl.DateTimeFormat('en-US', { month: 'long' });

     const getDaysInMonth = (date) => {
         const year = date.getFullYear();
         const month = date.getMonth();
         const end = new Date(year, month + 1, 0);
         const days = [];
 
         for (let i = 1; i <= end.getDate(); i++) {
             days.push(new Date(year, month, i));
         }
         return days;
     };
 
     const getFirstDayOfMonth = (date) => {
         return new Date(date.getFullYear(), date.getMonth(), 1).getDay();
     };
 
     const getDaysInMonthByWeeks = (date) => {
         const days = getDaysInMonth(date);
         const firstDayOfMonth = getFirstDayOfMonth(date);
         const weeks = [];
         let week = Array(firstDayOfMonth).fill(null);
 
         days.forEach(day => {
             if (week.length === 7) {
                 weeks.push(week);
                 week = [];
             }
             week.push(day);
         });
 
         if (week.length > 0) {
             while (week.length < 7) {
                 week.push(null);
             }
             weeks.push(week);
         }
         return weeks;
     };
 
     // 주 별 날짜
    const weeks = getDaysInMonthByWeeks(currentDate);
    const isEventDay = (date) => {
        if (date) {
            let day = date.getDate();
            return taskOfMonth.find(it => day >= it.taskStartDate[2] && day <= it.taskEndDate[2])?.taskContent || false;

        } else {
            return false;       
        }
    }
    const isToday = (date) => {
        if(date){
            if(date.getDate() === new Date().getDate()){
                return true;
            }
        } else {
            return false;
        }
    }
    // -------------------- 상태 관련 --------------------
    const onChangeRegist = (e) => {
        const { name, value } = e.target;
        setnewTask(t => ({
            ...t,
            [name]: value
        }));
    }
    const handleEdit = (taskCode) => {        
        setTaskList(taskList.map(item => 
            item.taskCode === taskCode ? { ...item, isEditing: !item.isEditing } : item
        ));
    }
    const logout = () => {
        localStorage.removeItem('token');
        nav('/');
    }
    
    // -------------------- API --------------------
    // 선택한 달에 할 일 조회 API
    const getTaskOfMonth = async() => {
        let calendarDate = currentDate.toISOString().split('T')[0];
        const res = await api.get(`/todo/tasks?calendarDate=${calendarDate}`);
        console.log(res);
        
        setTaskOfMonth(res.data);
    }
    // 선택한 날짜의 할 일 조회하는 API
    const getTaskOfDay = async() =>{
        try{
            const selectedDate = selectedDay.toLocaleDateString('en-CA').split('T')[0];
            const res = await api.get(`/todo/tasks/day?day=${selectedDate}`);
            setTaskList(res.data.data.map(item => ({ ...item, isEditing: false })));
        } catch(err){
            console.log(err);
        }
    }
    
    // 할 일 등록 API
    const registTask = async() => {
        await api.post('/todo/tasks',{
            taskCode: newTask.taskCode,
            taskContent: newTask.taskContent,
            taskStartDate: newTask.taskStartDate,
            taskEndDate: newTask.taskEndDate,
            taskState: newTask.taskState,
        });
        setTaskLoad(false);
        getTaskOfDay();
    }
    // 할 일 수정 API
    const modifyTask = (taskContent,taskCode) => {
        api.post(`/todo/tasks`,{
            taskCode: taskCode,
            taskContent: taskContent,
            taskState: false,
        })
    }
    // 할 일 삭제 API
    const deleteTask = async(taskCode) => {
        if(window.confirm("삭제하시겠습니까?")){
            const res = await api.delete(`/todo/tasks/${taskCode}`);
            getTaskOfDay();
            getTaskOfMonth();
            alert(res.data);  
        }
    }
    //카테고리 조회 API
    const getCategoryList = async() => {
        const res = await api.get(`/category`);
        setCategoryNames(res?.data);
    }

    // -------------------- useEffect --------------------
    useEffect(() => {
        if(!localStorage.getItem('token')){
            nav('/');
        } else {
            getCategoryList();
            getTaskOfMonth();
            setUser(jwtDecode(localStorage.getItem('token')));
        }


    }, []);

    useEffect(()=>{
        getTaskOfDay();
        setCurrentCategory('전체');
    },[selectedDay])
    // ---------------------------------------------------

    

    return <div className="todo-app">

    <div style={{backgroundColor:darkMode?'#494d5c':'#fff'}} className="calendar-container">
        {
            user.accountId ? 
            <div className="account-container">
                <div style={{display:'flex',alignItems:'center'}}>
                    <ProfileImage width={50} height={50}/>
                    <div style={{marginLeft:10,color:setStyle(darkMode,'text')}}>안녕하세요 <b>{user.accountNickname}</b> 님!</div> 
                </div>
                <div>
                    <button onClick={()=>nav('/test')}>Chat Room</button>
                    <button onClick={()=>nav('/mypage')}>My Page</button>
                    <button onClick={logout}>Logout</button>
                </div>
            </div>
            :
            <></>
        }
        <div className="calendar">

            <div className="header">
                <div className="header-btn-container" onClick={() => {
                    setCurrentDate(new Date(currentDate.setMonth(currentDate.getMonth() - 1)))
                    getTaskOfMonth();}}>
                <FontAwesomeIcon style={{color:setStyle(darkMode,'text')}} icon={faChevronLeft} className="faChevronLeft"/>
                </div>
                <h2 style={{color:setStyle(darkMode,'text')}}>{formatter.format(currentDate)} {currentDate.getFullYear()}</h2>
                <div className="header-btn-container" onClick={() => {
                    setCurrentDate(new Date(currentDate.setMonth(currentDate.getMonth() + 1)))
                    getTaskOfMonth();
                    }}>
                <FontAwesomeIcon style={{color:setStyle(darkMode,'text')}}  icon={faChevronRight} className="faChevronRight"/>
                </div>
            </div>

            <div style={{color:setStyle(darkMode,'text')}} className="days-of-week">
                {daysOfWeek.map(day => (
                    <div key={day} className="day-name">{day}</div>
                ))}
            </div>

            <div className="days">
                {weeks.map((week, weekIndex) => (
                    <div key={weekIndex} className="week">
                        {week.map((day, dayIndex) => (
                            <div onClick={() => {
                                setSelectedDay(day);
                            }} key={dayIndex} className={`day ${day ? '' : 'empty-day'}`}
                                style={{backgroundColor : day && (day.getDate() === selectedDay.getDate())
                                    && (day.getMonth() === selectedDay.getMonth())
                                    && (day.getFullYear() === selectedDay.getFullYear())
                                 ? "#829efb75" : "", 
                                 color: isEventDay(day) ? 'rgb(130, 158, 251)' : setStyle(darkMode,'text'),
                                 fontSize: isToday(day) ? 18 : 15,
                                 borderColor: isToday(day) ? 'black' : '#ddd',
                                 }}>
                                    <div style={{width:50}}>
                                        {day ? day.getDate() : ''}
                                        {isEventDay(day) ? <div style={{fontSize:10}}>{isEventDay(day)}</div> : isToday(day) ? <div style={{fontSize:13,color:'rgba(130, 158, 251, 0.46)'}}>Today</div> : <></>                              }
                                    </div>
                               
                                
                            </div>
                        ))}
                    </div>
                ))}
            </div>

        </div>

        <div style={{display: "flex",justifyContent: "space-between"}}>
            <div></div>
            <button className="today-btn" onClick={() => {
                setSelectedDay(new Date());
                setCurrentDate(new Date());
            }}>Today</button>
        </div>

    </div>


        {/* 오른쪽 UI
        - header에 카테고리 필터링 드롭다운
        - 캘린더 날짜를 클릭하면 해당 할 일 리스트 */}
    
        <div className="task-container">

            <header style={{display: "flex", justifyContent:"space-between", marginBottom: "20px"}}>
                <h1 className="selected-date">{selectedDay.getFullYear()}.{selectedDay.getMonth()+1}.{selectedDay.getDate()}</h1>
                                <div style={{display: "flex", justifyContent: "flex-end", alignItems:'center'}}>
                    <select style={{background: "#4f5b6f", border: "none", borderRadius: "5px",height:30,width:50}} onChange={(e) => {setCurrentCategory(e.target.value)}}>
                        <option value="전체">전체</option>
                        {categoryNames.map((category) => (
                            <option value={category.categoryName} key={category.categoryCode}>
                                {category.categoryName}
                            </option>
                        ))}
                    </select>
                </div>
            </header>


            {/* 할일 리스트 */}
            {!taskLoad ? 
            <>

                <div className="taskList">
                {
                    taskList.length === 0 ? 
                        <div style={{textAlign: "center", }}>할 일이 없습니다.</div>
                            : 
                        (currentCategory === "전체" ?
                            taskList.map((task, idx) => 
                                <div key={idx}>
                               <TaskList idx={idx} task={task} handleEdit={handleEdit} handleEdited={modifyTask} deleteTask={deleteTask} setCategoryNames={setCategoryNames} setTaskList={setTaskList}/>
                               </div>
                            )
                            :
                            taskList.filter((task) => task.taskCategoryName === currentCategory).map((task, idx) => 
                                <div key={idx}>
                                <TaskList idx={idx} task={task} handleEdit={handleEdit} handleEdited={modifyTask} deleteTask={deleteTask} setCategoryNames={setCategoryNames} setTaskList={setTaskList}/>
                                </div>

                            )
                        )
                }
                </div>
            </> 
            : 
            <>
                <div className="AddTask">
                    <div>할 일 추가</div><br />
                    <input type="text" name="taskContent" placeholder="내용" onChange={onChangeRegist}/><br /><br />
                    <input type="date" name="taskStartDate" onChange={onChangeRegist}/><br /><br />
                    <input type="date" name="taskEndDate" onChange={onChangeRegist}/><br /><br />
                    <button onClick={registTask}>추가하기</button>
                </div>
            </>}


            {/* 할 일 추가 버튼 */}
            <div style={{display: "flex", justifyContent: "flex-end"}}>
                <FontAwesomeIcon icon={faCirclePlus} className="faCirclePlus"  onClick={() => setTaskLoad(!taskLoad)} />
            </div>


        </div>

    </div>

}

export default Todo;