import MaterialTable from "material-table";
import { useEffect, useState } from "react";
import { Dropdown, Button, Menu,  } from 'semantic-ui-react'
import { TableIcons } from "../Utils/TableUtils";
import moment from "moment";
import { CloudUpload } from "@material-ui/icons";
import { token as token,  url as baseUrl } from "./../../../api";
import axios from "axios";
import SendToServer from "./SendToServer";

const GeneratedFilesList = (props) => {
    const [generatedFiles, setGeneratedFiles] = useState([]);
    const [sendToServerModalIsOpen, setSendToServerModalIsOpen] = useState(false);
    const [rowObj, setRowObj] = useState({});

    const toggleSendToServerModal = () => {
        setSendToServerModalIsOpen(!sendToServerModalIsOpen);
    }

    const goBack = () =>{
        props.setGenerateFilesGrid(false);
    }

    useEffect(() => {
        GetGeneratedFiles();
    }, [props.id])

    const sendToServerAction = (row) => {
        setRowObj({...row, syncHistoryTrackerUuid: row.uuid});
        toggleSendToServerModal();

    }
     // Get list of generated files
     async function GetGeneratedFiles() {
        axios
            .get(`${baseUrl}sync/history/${props.id}/tracker`,
           { headers: {"Authorization" : `Bearer ${token}`} }
            )
            .then((response) => {
                setGeneratedFiles(response.data);
            })
            .catch((error) => {
            });
    }

    return (
        <div>
            <SendToServer toggleModal={toggleSendToServerModal} showModal={sendToServerModalIsOpen} rowObj={rowObj}/>
            <Button
                variant="contained"
                style={{ backgroundColor: "#014d88", }}
                className=" float-right mr-1"
                onClick={goBack}
            >
                <span style={{ textTransform: "capitalize", color: "#fff" }}>{"<< Back"}</span>
            </Button>
            <br /><br />
            <MaterialTable
                icons={TableIcons}
                title="Generated Files List "
                columns={[
                    // { title: "Facility Name", field: "facilityName"},
                    { title: "File Name ", field: "fileName", filtering: false },
                    { title: "Record Size ", field: "recordSize", filtering: false },
                    { title: "Date Generated ", field: "date", filtering: false },
                    { title: "Status", field: "status", filtering: false },
                    { title: "Action", field: "actions", filtering: false },
                ]}
                data={generatedFiles.map((row) => ({
                    // facilityName: row.facilityName,
                    fileName: row.fileName,
                    recordSize: row.recordSize,
                    date: moment(row.timeCreated).format("LLLL"),
                    status: row.status,
                    //errorLog: row.errorLog,
                    actions: (<div>
                        <Menu.Menu position='right'  >
                            <Menu.Item >
                                <Button style={{ backgroundColor: 'rgb(153,46,98)' }} primary>
                                    <Dropdown item text='Action'>

                                        <Dropdown.Menu style={{ marginTop: "10px", }}>
                                            <Dropdown.Item disabled={row.status.toLowerCase() === 'synced'} onClick={() => sendToServerAction(row)}><CloudUpload color="primary" /> Send To Server
                                            </Dropdown.Item>
                                        </Dropdown.Menu>
                                    </Dropdown>
                                </Button>
                            </Menu.Item>
                        </Menu.Menu>
                    </div>)
                }))}

                options={{
                    headerStyle: {
                        backgroundColor: "#014d88",
                        color: "#fff",
                    },
                    searchFieldStyle: {
                        width: '200%',
                        margingLeft: '250px',
                    },
                    filtering: false,
                    exportButton: true,
                    searchFieldAlignment: 'left',
                    pageSizeOptions: [10, 20, 100],
                    pageSize: 10,
                    debounceInterval: 400
                }}
            />
        </div>
    )
}
export default GeneratedFilesList;