package Utilities.report;

import Utilities.DataUtils;
import Utilities.LogsUtils;
import com.google.common.collect.ImmutableMap;

import java.io.File;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

public class AllureEnvironmentManager {
    public static void setEnvironmentVariables() {
        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("OS", DataUtils.getProperty("os.name"))
                        .put("Java version:", DataUtils.getProperty("java.runtime.version"))
                        .build(), AllureConstants.RESULTS_FOLDER + File.separator
        );
        LogsUtils.info("Allure environment variables set.");
        AllureBinaryManager.downloadAndExtract();
    }
}

