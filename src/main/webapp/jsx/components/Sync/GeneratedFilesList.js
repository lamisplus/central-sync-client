import MaterialTable from "material-table";
import { useState } from "react";
import { Dropdown, Button, Menu,  } from 'semantic-ui-react'
import { TableIcons } from "../Utils/TableUtils";
import moment from "moment";
import { CloudUpload } from "@material-ui/icons";

const GeneratedFilesList = (props) => {
    const [generatedFiles, setGeneratedFiles] = useState([]);

    const goBack = () =>{
        props.setGenerateFilesGrid(false);
    }

    const sendToServerAction = (row) => {
        
    }

    return (
        <div>
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
                    { title: "Upload Size ", field: "uploadSize", filtering: false },
                    { title: "Upload Percentage ", field: "uploadPercentage", filtering: false },
                    { title: "Date Generated ", field: "date", filtering: false },
                    { title: "Status", field: "status", filtering: false },
                    { title: "Action", field: "actions", filtering: false },
                ]}
                data={generatedFiles.map((row) => ({
                    // facilityName: row.facilityName,
                    fileName: row.fileName,
                    uploadSize: row.uploadSize,
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