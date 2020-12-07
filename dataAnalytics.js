let inputCount = 0;

$(document).ready(()=> {
    $("button[name='add-button']").click(addElement);
    // $("button[type='submit']").click(submitData);
})

function addElement(e) {
    e.preventDefault();
    switch ($("#type").val()) {
        case "project":
            addProjectRow();
            break;
        case "regex":
            addRegexRow();
            break;
        case "filter":
            addFilterRow();
            break;
        case "limit":
            addLimitRow();
            break;
        case "sort":
            addSortRow();
            break;
        case "group":
            addGroupRow();
            break;
    
        default:
            break;
    }
}

function removeElement(e) {
    console.log("REMOVE");
    $(e.target).closest("div.row").remove();
}

function addProjectRow() {
    $("#add-stuff").append(
          '<div type="project" id="' + ++inputCount + '" class="form-group row">'
        + '    <label class="col-sm-2 col-form-label">Project Field</label>'
        + '    <div class="col-sm-8">'
        + '        <select name="project" id="project-' + inputCount + '" class="form-control">'
        + '            <option value="title">Title</option>'
        + '            <option value="userName">Username</option>'
        + '            <option value="productName">Product Name</option>'
        + '            <option value="productType">Product Type</option>'
        + '            <option value="productMaker">Product Maker</option>'
        + '            <option value="reviewRating">Rating</option>'
        + '            <option value="reviewDate">Review Date</option>'
        + '            <option value="reviewText">Review</option>'
        + '            <option value="retailerpin">Retailer PIN</option>'
        + '            <option value="retailercity">Retailer City</option>'
        + '            <option value="reviewerAge">Reviewer Age</option>'
        + '            <option value="reviewerGender">Reviewer Gender</option>'
        + '            <option value="reviewerOccupation">Reviewer Occupation</option>'
        + '            <option value="price">Product Price</option>'
        + '            <option value="pickupType">Pickup Type</option>'
        + '        </select>'
        + '    </div>'
        + '    <div class="col-sm-2">'
        + '        <button type="button" name="remove-button" id="remove-' + inputCount + '" class="buy-button btn text-light">Remove</button>'
        + '    </div>'
        + '</div>'
    );
    $("button#remove-" + inputCount).click(removeElement);
}

function addRegexRow() {
    $("#add-stuff").append(
          '<div type="regex" id="' + ++inputCount + '" class="form-group row">'
        + '    <label class="col-sm-2 col-form-label">Regex Find</label>'
        + '    <div class="col-sm-3">'
        + '        <select name="regex-field" id="regex-field-' + inputCount + '" class="form-control">'
        + '            <option value="title">Title</option>'
        + '            <option value="userName">Username</option>'
        + '            <option value="productName">Product Name</option>'
        + '            <option value="productType">Product Type</option>'
        + '            <option value="productMaker">Product Maker</option>'
        + '            <option value="reviewRating">Rating</option>'
        + '            <option value="reviewDate">Review Date</option>'
        + '            <option value="reviewText">Review</option>'
        + '            <option value="retailerpin">Retailer PIN</option>'
        + '            <option value="retailercity">Retailer City</option>'
        + '            <option value="reviewerAge">Reviewer Age</option>'
        + '            <option value="reviewerGender">Reviewer Gender</option>'
        + '            <option value="reviewerOccupation">Reviewer Occupation</option>'
        + '            <option value="price">Product Price</option>'
        + '            <option value="pickupType">Pickup Type</option>'
        + '        </select>'
        + '    </div>'
        + '    <label class="col-sm-2 col-form-label">Matches</label>'
        + '    <input class="col-sm-3 form-control" type="text" name="regex-pattern" id="regex-pattern-' + inputCount + '" placeholder="Pattern">'
        + '    <div class="col-sm-2">'
        + '        <button type="button" name="remove-button" id="remove-' + inputCount + '" class="buy-button btn text-light">Remove</button>'
        + '    </div>'
        + '</div>'
    )
    $("button#remove-" + inputCount).click(removeElement);
}

function addFilterRow() {
    $("#add-stuff").append(
          '<div type="filter" id="' + ++inputCount + '" class="form-group row">'
        + '    <label class="col-sm-1 col-form-label">Filter</label>'
        + '    <div class="col-sm-3">'
        + '        <select name="filter-field" id="filter-field-' + inputCount + '" class="form-control">'
        + '            <option value="title">Title</option>'
        + '            <option value="userName">Username</option>'
        + '            <option value="productName">Product Name</option>'
        + '            <option value="productType">Product Type</option>'
        + '            <option value="productMaker">Product Maker</option>'
        + '            <option value="reviewRating">Rating</option>'
        + '            <option value="reviewDate">Review Date</option>'
        + '            <option value="reviewText">Review</option>'
        + '            <option value="retailerpin">Retailer PIN</option>'
        + '            <option value="retailercity">Retailer City</option>'
        + '            <option value="reviewerAge">Reviewer Age</option>'
        + '            <option value="reviewerGender">Reviewer Gender</option>'
        + '            <option value="reviewerOccupation">Reviewer Occupation</option>'
        + '            <option value="price">Product Price</option>'
        + '            <option value="pickupType">Pickup Type</option>'
        + '        </select>'
        + '    </div>'
        + '    <div class="col-sm-3">'
        + '        <select name="filter-operator" id="filter-operator-' + inputCount + '" class="form-control">'
        + '            <option value="eq">==</option>'
        + '            <option value="lt"><</option>'
        + '            <option value="lte"><=</option>'
        + '            <option value="gt">></option>'
        + '            <option value="gte">>=</option>'
        + '        </select>'
        + '    </div>'
        + '    <input class="col-sm-3 form-control" type="text" name="filter-value" id="filter-value-' + inputCount + '" placeholder="Value">'
        + '    <div class="col-sm-2">'
        + '        <button type="button" name="remove-button" id="remove-' + inputCount + '" class="buy-button btn text-light">Remove</button>'
        + '    </div>'
        + '</div>'
    )
    $("button#remove-" + inputCount).click(removeElement);
}

function addLimitRow() {
    $("#add-stuff").append(
          '<div type="limit" id="' + ++inputCount + '" class="form-group row">'
        + '    <label class="col-sm-2 col-form-label">Limit By</label>'
        + '    <input class="col-sm-8 form-control" type="number" name="limit-number" id="limit-number-' + inputCount + '" placeholder="Number">'
        + '    <div class="col-sm-2">'
        + '        <button type="button" name="remove-button" id="remove-' + inputCount + '" class="buy-button btn text-light">Remove</button>'
        + '    </div>'
        + '</div>'
    )
    $("button#remove-" + inputCount).click(removeElement);
}

function addSortRow() {
    $("#add-stuff").append(
          '<div type="sort" id="' + ++inputCount + '" class="form-group row">'
        + '    <label class="col-sm-2 col-form-label">Sort By</label>'
        + '    <div class="col-sm-4">'
        + '        <select name="sort-field" id="sort-field-' + inputCount + '" class="form-control">'
        + '            <option value="title">Title</option>'
        + '            <option value="userName">Username</option>'
        + '            <option value="productName">Product Name</option>'
        + '            <option value="productType">Product Type</option>'
        + '            <option value="productMaker">Product Maker</option>'
        + '            <option value="reviewRating">Rating</option>'
        + '            <option value="reviewDate">Review Date</option>'
        + '            <option value="reviewText">Review</option>'
        + '            <option value="retailerpin">Retailer PIN</option>'
        + '            <option value="retailercity">Retailer City</option>'
        + '            <option value="reviewerAge">Reviewer Age</option>'
        + '            <option value="reviewerGender">Reviewer Gender</option>'
        + '            <option value="reviewerOccupation">Reviewer Occupation</option>'
        + '            <option value="price">Product Price</option>'
        + '            <option value="pickupType">Pickup Type</option>'
        + '        </select>'
        + '    </div>'
        + '    <div class="col-sm-4">'
        + '        <select name="sort-operator" id="sort-operator-' + inputCount + '" class="form-control">'
        + '            <option value="ascending">Ascending</option>'
        + '            <option value="descending">Descending</option>'
        + '        </select>'
        + '    </div>'
        + '    <div class="col-sm-2">'
        + '        <button type="button" name="remove-button" id="remove-' + inputCount + '" class="buy-button btn text-light">Remove</button>'
        + '    </div>'
        + '</div>'
    )
    $("button#remove-" + inputCount).click(removeElement);
}

function addGroupRow() {
    $("#add-stuff").append(
          '<div type="group" id="' + ++inputCount + '" class="form-group row">'
        + '    <label class="col-sm-1 col-form-label">Group By</label>'
        + '    <div class="col-sm-3">'
        + '        <select name="group-field" id="group-field-' + inputCount + '" class="form-control">'
        + '            <option value="title">Title</option>'
        + '            <option value="userName">Username</option>'
        + '            <option value="productName">Product Name</option>'
        + '            <option value="productType">Product Type</option>'
        + '            <option value="productMaker">Product Maker</option>'
        + '            <option value="reviewRating">Rating</option>'
        + '            <option value="reviewDate">Review Date</option>'
        + '            <option value="reviewText">Review</option>'
        + '            <option value="retailerpin">Retailer PIN</option>'
        + '            <option value="retailercity">Retailer City</option>'
        + '            <option value="reviewerAge">Reviewer Age</option>'
        + '            <option value="reviewerGender">Reviewer Gender</option>'
        + '            <option value="reviewerOccupation">Reviewer Occupation</option>'
        + '            <option value="price">Product Price</option>'
        + '            <option value="pickupType">Pickup Type</option>'
        + '        </select>'
        + '    </div>'
        + '    <div class="col-sm-3">'
        + '        <select name="group-operator" id="group-operator-' + inputCount + '" class="form-control">'
        + '            <option value="max">Max</option>'
        + '            <option value="count">Count</option>'
        + '        </select>'
        + '    </div>'
        + '    <div class="col-sm-3">'
        + '        <select name="group-max-field" id="group-max-field-' + inputCount + '" class="form-control">'
        + '            <option value="title">Title</option>'
        + '            <option value="userName">Username</option>'
        + '            <option value="productName">Product Name</option>'
        + '            <option value="productType">Product Type</option>'
        + '            <option value="productMaker">Product Maker</option>'
        + '            <option value="reviewRating">Rating</option>'
        + '            <option value="reviewDate">Review Date</option>'
        + '            <option value="reviewText">Review</option>'
        + '            <option value="retailerpin">Retailer PIN</option>'
        + '            <option value="retailercity">Retailer City</option>'
        + '            <option value="reviewerAge">Reviewer Age</option>'
        + '            <option value="reviewerGender">Reviewer Gender</option>'
        + '            <option value="reviewerOccupation">Reviewer Occupation</option>'
        + '            <option value="price">Product Price</option>'
        + '            <option value="pickupType">Pickup Type</option>'
        + '        </select>'
        + '    </div>'
        + '    <div class="col-sm-2">'
        + '        <button type="button" name="remove-button" id="remove-' + inputCount + '" class="buy-button btn text-light">Remove</button>'
        + '    </div>'
        + '</div>'
    )
    $("button#remove-" + inputCount).click(removeElement);
}

// function submitData(e) {
//     e.preventDefault();

//     let jsonData = [];
//     $("div.row.form-group").each(function() {
//         let attr = $(this).attr('type');
//         if (typeof attr == typeof undefined || attr == false) {
//             return;
//         }
        
//         switch ($(this).attr('type')) {
//             case "project":
//                 jsonData.push(getProjectData($(this).attr('id')));
//                 break;
//             case "regex":
//                 jsonData.push(getRegexData($(this).attr('id')));
//                 break;
//             case "filter":
//                 jsonData.push(getFilterData($(this).attr('id')));
//                 break;
//             case "limit":
//                 jsonData.push(getLimitData($(this).attr('id')));
//                 break;
//             case "sort":
//                 jsonData.push(getSortData($(this).attr('id')));
//                 break;
//             case "group":
//                 jsonData.push(getGroupData($(this).attr('id')));
//                 break;
        
//             default:
//                 break;
//         }
//     })

//     console.log(jsonData);
//     $.ajax({
//         url: "DataAnalytics",
//         type: "POST",
//         data: { data: JSON.stringify(jsonData) },
//         contentType: "application/x-www-form-urlencoded; charset=utf-8",
//         dataType: "json",
//         success: function (msg) {
//         }
//     })
// }

function getProjectData(id) {
    return {
        type: "project",
        field: $("#project-" + id).val()
    }
}

function getRegexData(id) {
    return {
        type: "regex",
        field: $("#regex-field-" + id).val(),
        pattern: $("#regex-pattern-" + id).val()
    }
}

function getFilterData(id) {
    return {
        type: "filter",
        field: $("#filter-field-" + id).val(),
        operator: $("#filter-operator-" + id).val(),
        value: $("#filter-value-" + id).val()
    }
}

function getLimitData(id) {
    return {
        type: "limit",
        number: $("#limit-number-" + id).val()
    }
}

function getSortData(id) {
    return {
        type: "sort",
        field: $("#sort-field-" + id).val(),
        operator: $("#sort-operator-" + id).val()
    }
}

function getGroupData(id) {
    return {
        type: "group",
        field: $("#group-field-" + id).val(),
        operator: $("#group-operator-" + id).val(),
        maxField: $("#group-max-field-" + id).val(),
    }
}
