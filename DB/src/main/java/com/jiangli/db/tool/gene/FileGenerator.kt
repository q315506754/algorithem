
import com.jiangli.common.utils.PathUtil
import org.apache.commons.io.IOUtils
import java.io.File
import java.io.FileOutputStream

/**
 *
 *
 * @author Jiangli
 * @date 2019/11/6 11:07
 */
fun generateFile(body:String, vararg path:String): File {
    val absPath = concatPath(*path)

    PathUtil.ensureFilePath(absPath)

    val fileOutputStream = FileOutputStream(absPath)
    IOUtils.write(body, fileOutputStream)
    fileOutputStream.flush()
    fileOutputStream.close()

    return File(absPath)
}


