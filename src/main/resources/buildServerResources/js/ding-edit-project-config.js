function saveDeploymentDashboardConfig(form, actionUrl) {
  $('errorSpanUrl').hidden = true
  $('errorSpanSafeParams').hidden = true
  $('errorSpanIpAddress').hidden = true
  let parameters = {
    projectExternalId: form.projectExternalId.value,
    dingNoticeEnabled: form.dingNoticeEnabled.checked,
    url: form.url.value,
    keyword: form.keyword.value,
    sign: form.sign.value,
    ipAddress: form.ipAddress.value,
  }
  const regExp = new RegExp(/http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- .\/?%&=]*)?/)
  if (!regExp.test(parameters.url)) {
    $('errorSpanUrl').hidden = false
    return false
  }
  if (!parameters.keyword && !parameters.sign && !parameters.ipAddress) {
    $('errorSpanSafeParams').hidden = false
    return false
  }
  if (parameters.ipAddress) {
    const regIp = new RegExp(/^(?!0)(?!.*\.$)((1?\d?\d|25[0-5]|2[0-4]\d)(\.|$)){4}$/)
    if (!regIp.test(parameters.ipAddress)) {
      $('errorSpanIpAddress').hidden = false
      return false;
    }
  }

  console.log("Saving ding notice config for " + form.projectExternalId + ": " + JSON.stringify(parameters))

  BS.ajaxRequest(actionUrl, {
    parameters: Object.toQueryString(parameters),

    onComplete: transport => {
      if (transport.responseXML) {
        BS.XMLResponse.processErrors(transport.responseXML, {
          onProfilerProblemError: elem => {
            alert(elem.firstChild.nodeValue)
          }
        });
      }
      BS.reload(true)
    }
  })

  return false

}
