var gulp = require('gulp');
var exec = require('child_process').exec;
const uglify = require('gulp-uglify');
const rename = require('gulp-rename');

gulp.task('minimize', ['compile'], function () {
    let options = {
        sourceMap: true,
        sourceMapIncludeSources: true,
        sourceMapRoot: './src/',
        mangle: true,
        compress: {
            sequences: true,
            dead_code: true,
            conditionals: true,
            booleans: true,
            unused: true,
            if_return: true,
            join_vars: true
        }
    };

    return gulp.src('target/bundle.js')
        .pipe(rename({extname: '.min.js'}))
            .pipe(uglify(options))
          ;
});

gulp.task('compile', function() {
  // 将你的默认的任务代码放在这
  console.log('default task start...');
  exec("npm run build",function(err){
	  exec("browserify target/myTool.js -o target/bundle.js",function(err){
	  
		});
  });
  
});

gulp.task('default', ['compile', 'minimize']);