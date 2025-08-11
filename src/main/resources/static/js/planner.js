// 현재 날짜
let current_year;
let current_month;
let current_date;
let current_day;

document.addEventListener('DOMContentLoaded', function() {
    console.log('DOCUMENT READY!!');

    // 오늘 날짜
    let today = new Date();
    let today_year = today.getFullYear();       // 오늘 년도
    let today_month = today.getMonth()          // 오늘 월  (0 ~ 11)
    let today_date = today.getDate()            // 오늘 일  
    let today_day = today.getDay()              // 오늘 요일 (0(일요일) ~ 6(토요일))

    // 현재 날짜 쎄팅
    setCurrentCalender(today_year, today_month, today_date, today_day);

    // 현재 <select> UI 렌더링
    setCurrentYearAndMonthSelectUI();

    // 현재 <tr> UI 렌더링
    addCalenderTr();

    // 일정들 출력
    fetchGetCurrentMonthPlans();

    // 이벤트 핸들러 정의
    initEvents();

});

function setCurrentCalender(year, month, date, day) {
    console.log('setCurrentCalender()');

    current_year = year;
    current_month = month;
    current_date = date;
    current_day = day;

}

function setCurrentYearAndMonthSelectUI() {
    console.log('setCurrentYearAndMonthSelectUI()');

    document.querySelector('#section_wrap select[name="p_year"]').value = current_year;
    document.querySelector('#section_wrap select[name="p_month"]').value = current_month + 1;

}

function addCalenderTr() {
    console.log('addCalenderTr()');

    let thisCalenderStart = new Date(current_year, current_month, 1);
    let thisCalenderStartDate = thisCalenderStart.getDate();        // 현재 월의 첫 날
    let thisCalenderStartDay = thisCalenderStart.getDay();          // 현재 월의 첫 요일

    let thisCalenderEnd = new Date(current_year, current_month + 1, 0);
    let thisCalenderEndDate = thisCalenderEnd.getDate();            // 현재 월의 마지막 날 (31)

    // 달력 구성 날짜 데이터
    let dates = Array();    // 날짜들
    let dateCnt = 1;
    for (let i = 0; i < 42; i++) {
        // i가 첫 날보다 작거나 또는 해당 날짜가 마지막 날짜보다 큰경우 >> 0
        if (i < thisCalenderStartDay || dateCnt > thisCalenderEndDate) {
            dates[i] = 0;
        } else {
            dates[i] = dateCnt;
            dateCnt++;
        }
    }

    // UI(<tr>)
    let tableBody = document.querySelector('#table_calender tbody');

    let dateIndex = 0;
    for (let i = 0; i < 6; i++) {

        if (i >= 5 && dates[dateIndex] === 0) break;

        let tr = document.createElement('tr');
        for(let j = 0; j < 7; j++) {
            let td = document.createElement('td');

            if (dates[dateIndex] !== 0) {
                // 날짜 UI
                let dateDiv = document.createElement('div');
                dateDiv.className = 'date';
                dateDiv.textContent = dates[dateIndex];
                td.appendChild(dateDiv);

                // 일정 등록 버튼 UI
                let writeDiv = document.createElement('div');
                let writeLink = document.createElement('a');
                writeLink.href = "#none";
                writeLink.className = 'write';
                writeLink.textContent = 'write';
                writeDiv.appendChild(writeLink);
                td.appendChild(writeDiv);

                // 일정 출력 UI
                let planWrap = document.createElement('div');
                planWrap.className = 'plan_wrap';
                planWrap.id = `date_${dates[dateIndex]}`;

                let planList = document.createElement('ul');
                planList.className = "plan";

                planWrap.appendChild(planList);
                td.appendChild(planWrap);
                /*
                    <td>
                        <div>10</div>
                        <div><a href="#none">write</div>
                        <div class="plan_wrap" id="date_10">
                            <ul class="plan">

                            </ul>
                        </div>
                    </td>
                */

            }

            tr.appendChild(td);
            dateIndex++;

        }

        tableBody.appendChild(tr);

    }

}

function initEvents() {
    console.log('initEvents()');

    document.addEventListener('click', function(event) {

        // 이전 달 버튼 클릭 시
        if (event.target.matches('#section_wrap .btn_pre')) {
            console.log('PRE BUTTON CLICKED!!');

            setPreMonth();

        }

        // 다음 달 버튼 클릭 시
        if (event.target.matches('#section_wrap .btn_next')) {
            console.log('NEXT BUTTON CLICKED!!');

            setNextMonth();

        }

        // 일정 등록 버튼 클릭 시 at 달력
        if (event.target.matches('#section_wrap a.write')) {

            let year = current_year;
            let month = current_month + 1;

            let dateElement = event.target.closest('div').parentElement.querySelector('div.date');
            let date = dateElement ? dateElement.textContent.trim() : "";

            showWritePlanView(year, month, date);

         }

        // 일정 등록 모달 닫기
        if (event.target.matches('#section_wrap input[value="CANCEL"]')) {
            
            hideWritePlanView();

        }

        // 일정 등록 모달에서 일정 등록 버튼 클릭 시
        if (event.target.matches('#section_wrap input[value="WRITE"]')) {
            console.log('WRITE BUTTON CLICKED!! AT MODAL');

            let year = document.querySelector("#write_plan select[name='wp_year']").value;      // 2025
            let month = document.querySelector("#write_plan select[name='wp_month']").value;    // 8
            let date = document.querySelector("#write_plan select[name='wp_date']").value;      // 6

            let title = document.querySelector("#write_plan input[name='p_title']").value;      // title
            let body = document.querySelector("#write_plan input[name='p_body']").value;        // body
            let file = document.querySelector("#write_plan input[name='p_file']").value;        // file

            if (title === '') {
                alert('INPUT NEW PLAN TITLE!!');
                document.querySelector("#write_plan input[name='p_title']").focus();

            } else if (body === '') {
                alert('INPUT NEW PLAN BODY!!');
                document.querySelector("#write_plan input[name='p_body']").focus();

            } else if (file === '') {
                alert('SELECT IMAGE FILE!!');
                document.querySelector("#write_plan input[name='p_file']").focus();
                
            } else {
                let inputFile = document.querySelector("#write_plan input[name='p_file']"); // InputFiles
                let files = inputFile.files;    // Array ['a']

                fetchWritePlan(year, month, date, title, body, files[0]);

            }

        }

        // 일정 디테일 모달 보기(달력에서 타이틀 클릭 시)
        if (event.target.matches('#table_calender a.title')) {
            console.log('PLAN TITLE CLICKED!!');

            fetchGetPlan(event.target.getAttribute('data-no'));

        }

        // 일정 디테일 모달 가리기
        if (event.target.matches('#show_plan input[value="CLOSE"]')) {
            console.log('DETAIL MODAL CLOSE BUTTON CLICKED!!');
            hideDetailPlanView();

        }

        // 일정 디테일 모달에서 DELETE 버튼 클릭 시
        if (event.target.matches('#show_plan input[value="DELETE"]')) {
            console.log('DETAIL MODAL DELETE BUTTON CLICKED!!');

            let no = event.target.getAttribute('data-no');
            fetchRemovePlan(no);

        }

        // 일정 디테일 모달에서 MODIFY 버튼 클릭 시
        if (event.target.matches('#show_plan input[value="MODIFY"]')) {
            console.log('DETAIL MODAL MODIFY BUTTON CLICKED!!');

            let year = document.querySelector('#show_plan select[name="dp_year"]').value;
            let month = document.querySelector('#show_plan select[name="dp_month"]').value;
            let date = document.querySelector('#show_plan select[name="dp_date"]').value;
            let title = document.querySelector('#show_plan input[name="p_title"]').value;
            let body = document.querySelector('#show_plan input[name="p_body"]').value;
            let no = event.target.getAttribute('data-no');
            let fileInput = document.querySelector('#show_plan input[name="p_file"]');
            let file = fileInput.files[0];

            fetchModifyPlan(no, year, month, date, title, body, file);

        }

    });

    document.addEventListener('change', function(event){

        // <select name="p_year">에서 발생한 change 이벤트
        if (event.target.matches('#section_wrap select[name="p_year"]')) {
            console.log('p_year CHANGED!!');

            setMonthBySelectChanged();

        }

        // <select name="p_month">에서 발생한 change 이벤트
        if (event.target.matches('#section_wrap select[name="p_month"]')) {
            console.log('p_month CHANGED!!');

            setMonthBySelectChanged();

        }

    });

}

function setPreMonth() {
    console.log('setPreMonth()');

    let yearSelect = document.querySelector('select[name="p_year"]');
    let monthSelect = document.querySelector('select[name="p_month"]');

    // 2024. 01
    if (yearSelect.value == 2024 && monthSelect.value == 1) {
        alert("2024년 1월 이전은 설정할 수 없어요.^^");
        return false;
    }

    let temp_year = current_year;           // 2025
    let temp_month = current_month - 1;     // 6(5)

    let preCalender = new Date(temp_year, temp_month, 1);

    // 현재 날짜 데이터 새로 쎄팅
    setCurrentCalender(
        preCalender.getFullYear(),
        preCalender.getMonth(),
        preCalender.getDate(),
        preCalender.getDay()
    )

    // <select> UI 렌더링
    setCurrentYearAndMonthSelectUI();

    // 기존 <tr> 제거
    removeCalenderTr();

    // <tr> 새로 렌더링
    addCalenderTr();

    fetchGetCurrentMonthPlans();

}

function setNextMonth() {
    console.log('setNextMonth()');

    let yearSelect = document.querySelector('select[name="p_year"]');
    let monthSelect = document.querySelector('select[name="p_month"]');

    if (yearSelect.value == 2030 && monthSelect.value == 12) {
        alert("2030년 12월 이후는 설정할 수 없습니다.");
        return false;

    }

    let temp_year = current_year;
    let temp_month = current_month + 1;

    // 현재 날짜 데이터 새로 쎄팅
    let nextCalender = new Date(temp_year, temp_month, 1);
    setCurrentCalender(
        nextCalender.getFullYear(),
        nextCalender.getMonth(),
        nextCalender.getDate(),
        nextCalender.getDay()
    )

    // <select> UI 렌더링
    setCurrentYearAndMonthSelectUI();

    // 기존 <tr> 제거
    removeCalenderTr();

    // <tr> 새로 렌더링
    addCalenderTr();

    fetchGetCurrentMonthPlans();

}

function removeCalenderTr() {
    console.log('removeCalenderTr()');

    let tbody = document.querySelector('#table_calender tbody');
    tbody.innerHTML = '';

}

function setMonthBySelectChanged() {
    console.log('setMonthBySelectChanged()');

    let temp_year = document.querySelector('select[name="p_year"]').value;
    let temp_month = document.querySelector('select[name="p_month"]').value - 1;

    let selectedCalender = new Date(temp_year, temp_month, 1);

    // 데이터
    setCurrentCalender(
        selectedCalender.getFullYear(),
        selectedCalender.getMonth(),
        selectedCalender.getDate(),
        selectedCalender.getDay()
    )

    // UI
    removeCalenderTr();
    addCalenderTr();
    fetchGetCurrentMonthPlans();

}

function showWritePlanView(year, month, date) {
    console.log('showWritePlanView()');

    document.querySelector('#write_plan select[name="wp_year"]').value = year;
    document.querySelector('#write_plan select[name="wp_month"]').value = month;

    setSelectDateOptions(year, month, 'wp_date');
    document.querySelector('#write_plan select[name="wp_date"]').value = date;

    document.querySelector('#write_plan').style.display = 'block';

}

function setSelectDateOptions(year, month, select_name) {
    console.log('setSelectDateOptions()');

    // SET DATA
    let last = new Date(year, month, 0);

    // UI
    let selectElement = document.querySelector(`select[name="${select_name}"]`);
    for (let i = 1; i <= last.getDate(); i++) {
        let option = document.createElement('option');
        option.value = i;
        option.textContent = i;
        selectElement.appendChild(option);
    }

}

function hideWritePlanView() {
    console.log('hideWritePlanView()');

    document.querySelector('#write_plan input[name="p_title"]').value = '';
    document.querySelector('#write_plan input[name="p_body"]').value = '';
    document.querySelector('#write_plan input[name="p_file"]').value = '';

    document.querySelector('#write_plan').style.display = 'none';

}

function showDetailPlanView(plan) {
    console.log('showDetailPlanView()');

    // 데이터 설정
    const showPlan = document.querySelector('#show_plan');

    showPlan.querySelector('select[name="dp_year"]').value = plan.year;
    showPlan.querySelector('select[name="dp_month"]').value = plan.month;

    setSelectDateOptions(plan.year, plan.month, "dp_date");
    showPlan.querySelector('select[name="dp_date"]').value = plan.date;

    showPlan.querySelector('input[name="p_title"]').value = plan.title;
    showPlan.querySelector('input[name="p_body"]').value = plan.body;

    // 이미지 데이터 설정 (plan.img_name)  /planUploadImg
    let uploadImgURI = `/planUploadImg/${plan.owner_id}/${plan.img_name}`;
    showPlan.querySelector('img.plan_img').src = uploadImgURI;

    showPlan.querySelectorAll('input').forEach(input => input.dataset.no = plan.no);

    showPlan.style.display = "block";

}

function hideDetailPlanView() {
    console.log('hideDetailPlanView()');

    document.querySelector('#show_plan input[name="p_title"]').value = '';
    document.querySelector('#show_plan input[name="p_body"]').value = '';
    document.querySelector('#show_plan input[name="p_file"]').value = '';

    const showPlan = document.querySelector('#show_plan');
    showPlan.style.display = "none";

}