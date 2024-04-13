package nel.marco.approval

import nel.marco.AccessRequest

interface ApprovalService{
    fun approveRequest(accessRequest: AccessRequest)
    fun declineRequest(accessRequest: AccessRequest)
}