// 일정 등록 하기
async function fetchWritePlan(year, month, date, title, body, file) {
    console.log('fetchWritePlan()');

    // POST
    let reqFormData = new FormData();
    reqFormData.append('year', year);
    reqFormData.append('month', month);
    reqFormData.append('date', date);
    reqFormData.append('title', title);
    reqFormData.append('body', body);
    reqFormData.append('file', file);

    try {
        let response = await fetch(
                            '/planner/plan', 
                            {
                                method: 'POST',
                                body: reqFormData,
                            });
        
        if (!response.ok) {
            throw new Error('통신 실패!!');

        }

        console.log('fetchWritePlan() COMMUNICATION SUCCESS!!');
        let data = await response.json();
        if (!data || data.result <= 0) {
            alert('일정 등록에 문제가 발생 했습니다.');

        } else {
            alert('일정이 정상적으로 등록 되었습니다.');

            // 캘린더 지우기
            removeCalenderTr();

            // 캘린더 새로 그리기
            addCalenderTr();

            // 이번달 나의 일정들을 가져와서 새로 그린 캘린더에 출력
            fetchGetCurrentMonthPlans();

        }

    } catch (e) {
        console.log('fetchWritePlan() COMMUNICATION FAIL!! ', e);
        alert('일정 등록에 문제가 발생했습니다.');

    } finally {
        console.log('fetchWritePlan() COMMUNICATION COMPLETE!!');
        hideWritePlanView();

    }

}

async function fetchGetCurrentMonthPlans() {
    console.log('fetchGetCurrentMonthPlans()');

    let reqData = {
        year: current_year,
        month: current_month + 1
    }

    try {
        let queryString = new URLSearchParams(reqData).toString();

        let response = await fetch(
            `/planner/plans?${queryString}`, 
            {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json; charset=utf-8',
                }
            });
        
        if (!response.ok) {
            console.log('COMMUNICATION FAIL!!');
        }

        console.log('COMMUNICATION SUCCESS!!');

        let data = await response.json();
        let plans = data.plans;
        console.log('plans: ', plans);
        
        plans.forEach(dto => {

            let appedTag = `<li><a class="title" data-no="${dto.no}" href="#none">${dto.title}</a></li>`;
            let targetElement = document.querySelector(`#date_${dto.date} ul.plan`);
            if (targetElement) {
                targetElement.insertAdjacentHTML('beforebegin', appedTag);
            }

        });

    } catch (e) {

    }

}

async function fetchGetPlan(no) {
    console.log('fetchGetPlan()');

    let reqData = new URLSearchParams({
        'no': no,
    });

    try{
        let response =  await fetch(
            `/planner/plan?${reqData.toString()}`,
            {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json; charset=utf-8',
                }
            }
        );

        if(!response.ok) {
            throw new Error('COMMUNICATION ERROR');
        }

        let data = await response.json();
        
        showDetailPlanView(data.plan);


    } catch(e) {
        console.log('COMMUNICATION ERROR!!', e);

    }
    
}

async function fetchRemovePlan(no) {
    console.log('fetchRemovePlan()');

    try{
        let response =  await fetch(
            `/planner/plan/${no}`,
            {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json; charset=utf-8',
                }
            }
        );

        if(!response.ok) {
            throw new Error('COMMUNICATION ERROR');
        }

        let data = await response.json();
        if (data.result > 0) {
            alert('일정이 정상적으로 삭제 되었습니다.');
            
            removeCalenderTr();
            addCalenderTr();
            fetchGetCurrentMonthPlans();

        } else {
            alert('일정 삭제에 문제가 발생했습니다. 잠시후 다시 시도해 주세요.');

        }


    } catch(e) {
        console.log('COMMUNICATION ERROR!!', e);

    } finally {
        hideDetailPlanView();

    }

}

async function fetchModifyPlan(no, year, month, date, title, body, file) {
    console.log('fetchModifyPlan()');

    let formData = new FormData();
    formData.append("year", year);
    formData.append("month", month);
    formData.append("date", date);
    formData.append("title", title);
    formData.append("body", body);

    if (file != undefined) {
        formData.append('file', file);
    }

    try {
        let response = await fetch(
            `/planner/plan/${no}`, 
            {
                method: 'PUT', 
                body: formData,
            }
        );

        if (!response.ok) {
            throw new Error('NETWORK ERROR!!');
        }

        let data = await response.json();
        if (data.result <= 0) {
            alert('일정 수정에 문제가 발생했습니다.');

        } else {
            alert('일정이 정상적으로 수정 됐습니다.');

            removeCalenderTr();
            addCalenderTr();
            fetchGetCurrentMonthPlans();

        }

    } catch (e) {
        console.log('COMMUNITICATION ERROR!!');
        alert('일정 수정에 문제가 발생했습니다.');

    } finally {
        hideDetailPlanView();

    }
    
}