package simon.trebis.job

import com.evernote.android.job.Job
import com.evernote.android.job.JobManager
import com.evernote.android.job.JobRequest
import com.evernote.android.job.util.support.PersistableBundleCompat
import simon.trebis.Const
import simon.trebis.Const.Companion.WEBSITE_ID
import simon.trebis.Const.Companion.WEBSITE_URL
import simon.trebis.service.DownloadService
import java.util.concurrent.TimeUnit


class TrebisDownloadJob : Job() {

    companion object {
        const val TAG = "DOWNLOAD_JOB"
    }

    override fun onRunJob(params: Job.Params): Job.Result {
        params.extras.apply {
            DownloadService.startFetchAction(
                    context,
                    getString(WEBSITE_URL, ""),
                    getLong(WEBSITE_ID, -1)
            )
        }
        return Job.Result.SUCCESS
    }

    fun schedule(websiteId: Long, url: String): Int {
        val extras = PersistableBundleCompat().apply {
            putLong(Const.WEBSITE_ID, websiteId)
            putString(Const.WEBSITE_URL, url)
        }

        scheduleImmediate(extras)
        return schedulePeriodic(extras)
    }

    private fun scheduleImmediate(extras: PersistableBundleCompat) {
        JobRequest.Builder(TAG)
                .startNow()
                .setUpdateCurrent(false)
                .setExtras(extras)
                .build()
                .schedule()
    }

    private fun schedulePeriodic(extras: PersistableBundleCompat): Int {
        return JobRequest.Builder(TAG)
                .setPeriodic(TimeUnit.MINUTES.toMillis(15), TimeUnit.MINUTES.toMillis(5))
                .setRequiredNetworkType(JobRequest.NetworkType.UNMETERED)
                .setRequiresStorageNotLow(true)
                .setRequirementsEnforced(true)
                .setUpdateCurrent(false)
                .setExtras(extras)
                .build()
                .schedule()
    }

    fun cancelById(jobId: Int) {
        JobManager.instance().cancel(jobId)
    }

}
