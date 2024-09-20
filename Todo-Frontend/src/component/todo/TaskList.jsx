import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCircle } from "@fortawesome/free-regular-svg-icons";
import { faChevronRight, faPen, faXmark, faCheck } from "@fortawesome/free-solid-svg-icons";
import { useEffect, useState } from "react";
import api from "../../AxiosInterceptor";



const TaskList = ({task, idx, handleEdit, handleEdited, deleteTask, setTaskList}) => {

    //회의 준비는 첨부터 true임

    const [status, setStatus] = useState(task.taskStatus);
    const setTaskStatus = () => {
        api.post('/todo/tasks',{taskCode:task.taskCode,taskState : !task.taskState});
        setStatus(!status);
    }
    useEffect(()=>{
        setStatus(task.taskState);
    },[task])

    return  <div className="task" key={idx}>
        {
            status ? 
            <FontAwesomeIcon onClick={setTaskStatus} icon={faCheck} style={{color: "#FFD43B",}} /> 
            : 
            <FontAwesomeIcon onClick={setTaskStatus} icon={faCircle} size="sm" className="faCircle" />
        }
    
    { task.isEditing ?
    <>
        <div style={{width:'100%' ,display: "flex", justifyContent:"space-between"}}>
            <input type="text" value={task.taskContent}  onChange={(e) => setTaskList(u =>{
                const update = [...u];
                update[idx] = {...update[idx], taskContent: e.target.value};
                return update;
            })}/>
            <FontAwesomeIcon icon={faChevronRight} fade className="faChevronRight" 
                onClick={() => {handleEdit(task.taskCode); handleEdited(task.taskContent,task.taskCode);}}
            />
        </div>
    </>
    :<div style={{width:'100%' ,display: "flex", justifyContent:"space-between"}}>
        <div style={{marginLeft: "10px"}}>{task.taskContent}</div>
        <div>
            <FontAwesomeIcon icon={faPen} className="faPen" onClick={() => handleEdit(task.taskCode)} style={{color: "#829efb75",}}/>&nbsp;&nbsp;&nbsp;
            <FontAwesomeIcon icon={faXmark} className="faXmark" onClick={() => deleteTask(task.taskCode)} style={{color: "#829efb75",}}/>
        </div>
    </div>
    }
    
</div>
}

export default TaskList;